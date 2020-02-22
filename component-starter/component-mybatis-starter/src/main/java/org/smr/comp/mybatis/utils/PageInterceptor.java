package org.smr.comp.mybatis.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.blade.utils.Pager;

import java.sql.*;
import java.util.Properties;

/**
 * 通过拦截<code>StatementHandler</code>中<code>prepare</code>方法，重写sql语句实现物理分页
 * 老规矩，签名里要拦截的类型只能是接口
 * 
 * @author 湖畔微风
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class})})
public class PageInterceptor implements Interceptor {
	
    private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    
	private PagerSqlBuilder pageSqlBuilder;
	
	/**
	 * 初始化，要求要对数据库进行配置
	 * @param dbType 数据库类型，ORACLE,MYSQL,SQLSERVER2005+
	 */
	public PageInterceptor(String dbType){
		if(dbType.equals("ORACLE")){
			pageSqlBuilder = new OraclePageSqlBuilder();
		}else if(dbType.equals("MYSQL")){
			pageSqlBuilder = new MySQLPageSqlBuilder ();
		}else if(dbType.equals("SQLSERVER2005+")){
			pageSqlBuilder = new MsSQLPageSqlBuilder ();
		}else{
			pageSqlBuilder = new MsSQLPageSqlBuilder ();
		}
	}

    /**
     * 初始化，要求要对数据库进行配置
     */
    public PageInterceptor(){

        pageSqlBuilder = new MySQLPageSqlBuilder ();
        logger.debug(" mybatis 拦载器初始化，默认选中：MySQLPageSqlBuilder");

    }

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
                
        // 分离代理对象(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出原始的的目标对象)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        }
        // 分离后一个代理对象的目标对象
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。经过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
        if (mappedStatement.getId().toLowerCase().indexOf("pagination") > 0) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            } else {
                Pager page = (Pager) metaStatementHandler
                        .getValue("delegate.boundSql.parameterObject.page");
                String sql = boundSql.getSql();

                // 重写sql
                Long pageSize = page.getPageSize();
                Long startIndex = (page.getCurrentPage()-1 )* pageSize;

                if(StringUtils.isBlank(page.getOrderByClause())){
                   page.setOrderByClause("");
                }
                String pageSql = pageSqlBuilder.buildPageSql(sql,startIndex ,pageSize, page.getOrderByClause());
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // 采用物理分页后，就不要mybatis的内存分页了，所以重置下面的两个参数
                metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                Connection connection = (Connection) invocation.getArgs()[0];
                // 重设分页参数里的总页数等
                setPageParameter(sql, connection, mappedStatement, boundSql, page);

            }
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    /**
     * 从数据库里查询记录数并计算总页数,
     * 回写进分页参数<code>PageParameter</code>,
     * 这样调用者就可用通过 分页参数
     * <code>PageParameter</code>获得相关信息
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, Pager page) {


        // 记录总记录数
        String countSql = pageSqlBuilder.getCountSql(sql);
        logger.debug("记录总记录数 sql:{}", countSql);
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            Long totalCount = 0L;
            if (rs.next()) {
                totalCount = rs.getLong(1);
            }
            page.setTotalCount(totalCount);
            logger.debug(" totalCount = {}" , totalCount);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }

    }

    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }


    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties properties) {
    }

	public PagerSqlBuilder getPageSqlBuilder() {
		return pageSqlBuilder;
	}

	public void setPageSqlBuilder(PagerSqlBuilder pageSqlBuilder) {
		this.pageSqlBuilder = pageSqlBuilder;
	}
    
    

}
