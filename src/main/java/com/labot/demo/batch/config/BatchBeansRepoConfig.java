package com.labot.demo.batch.config;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchBeansRepoConfig {

    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    public BatchBeansRepoConfig(PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Value("${batch.table.prefix:BATCH_}")
    private String tablePrefix;

    /**
     * This bean is to set the jobRepository using a prefix on Tables replacing BATCH_ with batch.BATCH_
     * This is useful when the Spring Batch tables are on another Schema
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "batchJobRepository")
    public JobRepository jobRepo(DataSource dataSource) throws Exception {
        final JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTablePrefix(tablePrefix);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "batchJobExplorer")
    public JobExplorer jobCustomExplorer() throws Exception {
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTablePrefix(tablePrefix);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

}
