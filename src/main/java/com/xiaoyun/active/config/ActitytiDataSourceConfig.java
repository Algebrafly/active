package com.xiaoyun.active.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author：xusonglin ===============================
 * Created with IDEA.
 * Date：18-11-5
 * Time：上午10:01
 * ================================
 */
@Component
@Slf4j
public class ActitytiDataSourceConfig extends AbstractProcessEngineAutoConfiguration {


    @Autowired
    private UUIDGenerator uuidGenerator;

    @Bean(name = "activitiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource activitiDataSource(){
        log.info("activitiDataSource 初始化...");
        return new DruidDataSource();
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("activitiDataSource") DataSource activitiDataSource) {
        return new DataSourceTransactionManager(activitiDataSource);
    }

    @Bean
    public DbSqlSessionFactory dbSqlSessionFactory(){
        return new DbSqlSessionFactory();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(@Qualifier("activitiDataSource") DataSource activitiDataSource,
                                                                             @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
                                                                             @Qualifier("dbSqlSessionFactory") DbSqlSessionFactory dbSqlSessionFactory) {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(activitiDataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager);
        configuration.setDbSqlSessionFactory(dbSqlSessionFactory);
        //设置ProcessEngineConfigurationImpl里的uuidGenerator
        configuration.setIdGenerator(uuidGenerator);
        //设置DbSqlSessionFactory的uuidGenerator，否则流程id，任务id，实例id依然是用DbIdGenerator生成
        configuration.getDbSqlSessionFactory().setIdGenerator(uuidGenerator);
        return configuration;
    }

}