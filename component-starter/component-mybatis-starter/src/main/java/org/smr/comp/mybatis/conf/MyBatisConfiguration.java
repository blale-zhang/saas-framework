package org.smr.comp.mybatis.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.comp.mybatis.utils.PageInterceptor;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
@EnableConfigurationProperties({DataSourceProperties.class, MyBatisProperties.class})
public class MyBatisConfiguration  implements EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private  ResourceLoader resourceLoader;

    @Autowired
    private Environment env;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private MyBatisProperties myBatisProperties;


    public MyBatisConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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
    public SqlSessionFactory sqlSessionFactory() throws Exception{


        if (this.myBatisProperties.isCheckConfigLocation() && StringUtils.hasText(this.myBatisProperties.getConfigLocation())) {
            Resource resource = this.resourceLoader.getResource(this.myBatisProperties.getConfigLocation());
            Assert.state(resource.exists(), "Cannot find config location: " + resource
                    + " (please add config file or check your Mybatis configuration)");
        }

        logger.debug("初始化:{}",SqlSessionFactory.class);
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(getDataSource());
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        if (StringUtils.hasLength(myBatisProperties.getTypeAliasesPackage())) {
            sfb.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());
        }
        logger.debug("SqlSessionFactory setTypeAliasesPackage:{}", myBatisProperties.getTypeAliasesPackage());

        if (!ObjectUtils.isEmpty(myBatisProperties.getMapperLocations())) {
            sfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(myBatisProperties.getMapperLocations()));
        }
        logger.debug("SqlSessionFactory setMapperLocations:{}", myBatisProperties.getMapperLocations());

        sfb.setPlugins(new Interceptor[]{new PageInterceptor()});
        logger.debug("SqlSessionFactory setPlugins:{}", PageInterceptor.class);

        return sfb.getObject();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }


    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = this.myBatisProperties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

}

