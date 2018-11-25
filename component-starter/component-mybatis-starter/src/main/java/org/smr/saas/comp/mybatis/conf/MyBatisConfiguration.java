package org.smr.saas.comp.mybatis.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.smr.saas.comp.mybatis.utils.PageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName: MyBatisConfig
 * @Description: Spring Boot集成Mybatis的基本入口
 * @author S1ow
 * @date 2017年5月23日 上午9:59:56
 *
 */
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties({DataSourceProperties.class, MyBatisProperties.class})
public class MyBatisConfiguration  implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Autowired
    private Environment env;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private MyBatisProperties myBatisProperties;

    /**
     * @Title: getDataSource
     * @Description: 创建数据源
     * @param @return
     * @return DataSource
     * @throws
     */
    @Bean
    public DataSource getDataSource(){
        Properties props = new Properties();
        props.put("driverClass", dataSourceProperties.getDriverClassName());
        props.put("url", dataSourceProperties.getUrl());
        props.put("username",dataSourceProperties.getUsername());
        props.put("password", dataSourceProperties.getPassword());

        props.put("initialSize", dataSourceProperties.getDruid().getInitialSize());
        props.put("maxActive", dataSourceProperties.getDruid().getMaxActive());
        props.put("maxWait", dataSourceProperties.getDruid().getMaxWait());
        props.put("timeBetweenEvictionRunsMillis", dataSourceProperties.getDruid().getTimeBetweenEvictionRunsMillis());

        props.put("minEvictableIdleTimeMillis", dataSourceProperties.getDruid().getMinEvictableIdleTimeMillis());
        props.put("maxEvictableIdleTimeMillis", dataSourceProperties.getDruid().getMaxEvictableIdleTimeMillis());
        props.put("validationQuery", dataSourceProperties.getDruid().getValidationQuery());
        props.put("testWhileIdle", dataSourceProperties.getDruid().getTestWhileIdle());

        props.put("testOnBorrow", dataSourceProperties.getDruid().getTestOnBorrow());
        props.put("testOnReturn", dataSourceProperties.getDruid().getTestOnReturn());
        props.put("poolPreparedStatements", dataSourceProperties.getDruid().getPoolPreparedStatements());
        props.put("maxPoolPreparedStatementPerConnectionSize", dataSourceProperties.getDruid().getMaxPoolPreparedStatementPerConnectionSize());
        props.put("filters", dataSourceProperties.getDruid().getFilters());
        props.put("connectionProperties", dataSourceProperties.getDruid().getConnectionProperties());
        props.put("useGlobalDataSourceStat",dataSourceProperties.getDruid().getUseGlobalDataSourceStat());
        props.put("aopPatterns", dataSourceProperties.getDruid().getAopPatterns());

        try {
            return DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   /* //注册dataSource
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException {
        if (StringUtils.isEmpty(propertyResolver.getProperty("url"))) {
            System.out.println("Your database connection pool configuration is incorrect!"
                    + " Please check your Spring profile, current profiles are:"
                    + Arrays.toString(env.getActiveProfiles()));
            throw new ApplicationContextException(
                    "Database connection pool is not configured correctly");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        druidDataSource.setUrl(propertyResolver.getProperty("url"));
        druidDataSource.setUsername(propertyResolver.getProperty("username"));
        druidDataSource.setPassword(propertyResolver.getProperty("password"));
        druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
        druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
        druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
        druidDataSource.setFilters(propertyResolver.getProperty("filters"));
        return druidDataSource;
    }
*/

    /**
     * @Title: sqlSessionFactory
     * @Description:  根据数据源创建SqlSessionFactory
     * @param @param ds
     * @param @return
     * @param @throws Exception
     * @return SqlSessionFactory
     * @throws
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(ds);
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        sfb.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());
        sfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(myBatisProperties.getMapperLocations()));
        sfb.setPlugins(new Interceptor[]{new PageInterceptor()});
        return sfb.getObject();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }
}

