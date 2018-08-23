package org.smr.saas.comp.mybatis.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @ClassName: MyBatisConfig
 * @Description: Spring Boot集成Mybatis的基本入口
 * @author S1ow
 * @date 2017年5月23日 上午9:59:56
 *
 */
@Configuration
@MapperScan(basePackages="org.smr")
@EnableConfigurationProperties({DataSourceProperties.class, Druid.class, MyBatisProperties.class})
public class MyBatisConfiguration {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private Druid druid;

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

        props.put("initialSize", druid.getInitialSize());
        props.put("maxActive", druid.getMaxActive());
        props.put("maxWait", druid.getMaxWait());
        props.put("timeBetweenEvictionRunsMillis", druid.getTimeBetweenEvictionRunsMillis());

        props.put("minEvictableIdleTimeMillis", druid.getMinEvictableIdleTimeMillis());
        props.put("maxEvictableIdleTimeMillis", druid.getMaxEvictableIdleTimeMillis());
        props.put("validationQuery", druid.getValidationQuery());
        props.put("testWhileIdle", druid.getTestWhileIdle());

        props.put("testOnBorrow", druid.getTestOnBorrow());
        props.put("testOnReturn", druid.getTestOnReturn());
        props.put("poolPreparedStatements", druid.getPoolPreparedStatements());
        props.put("maxPoolPreparedStatementPerConnectionSize", druid.getMaxPoolPreparedStatementPerConnectionSize());
        props.put("filters", druid.getFilters());
        props.put("connectionProperties", druid.getConnectionProperties());
        props.put("useGlobalDataSourceStat",druid.getUseGlobalDataSourceStat());
        props.put("aopPatterns", druid.getAopPatterns());

        try {
            return DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
        return sfb.getObject();
    }
}
