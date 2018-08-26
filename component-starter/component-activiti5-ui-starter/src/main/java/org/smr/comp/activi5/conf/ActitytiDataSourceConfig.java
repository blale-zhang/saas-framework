package org.smr.comp.activi5.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ActitytiDataSourceConfig extends AbstractProcessEngineAutoConfiguration {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActitytiDataSourceConfig.class);


    @Bean(name = "activitiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.activiti")
    public DataSource activitiDataSource(){
        log.info("activitiDataSource 初始化...");
        return new DruidDataSource();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(activitiDataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(activitiDataSource());
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager());
        return configuration;
    }

}
