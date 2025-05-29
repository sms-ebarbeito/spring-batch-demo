package com.labot.demo.batch.config;

import com.labot.demo.batch.processor.DelegatedItemProcessorService;
import com.labot.demo.batch.processor.ServiceItemProcessorAbstract;
import com.labot.demo.batch.reader.DelegatedItemReaderRepository;
import com.labot.demo.batch.reader.RepositoryItemReaderAbstract;
import com.labot.demo.batch.service.BatchImportListener;
import com.labot.demo.batch.service.BatchImportService;
import com.labot.demo.batch.writer.DelegatedItemWriterService;
import com.labot.demo.batch.writer.ServiceItemWriterAbstract;
import lombok.Setter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final BatchImportService batchImportService;


    @Value("${batch.import.threads:1}")
    @Setter
    private Integer importThreads;

    @Value("${batch.import.chunk:10}")
    @Setter
    private Integer chunk;

    @Value("${batch.table.prefix:BATCH_}")
    private String tablePrefix;

    public BatchConfig(@Qualifier("batchJobRepository") JobRepository jobRepository,
                       PlatformTransactionManager transactionManager,
                       BatchImportService batchImportService) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.batchImportService = batchImportService;
    }

    @Bean
    @Qualifier("taskExecutor")
    public SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("!SPRING_BATCH_");
    }

    @Bean
    @Qualifier("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(importThreads);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix(tablePrefix);
        executor.initialize();
        return executor;
    }

    @Bean(name = "jobBatchLauncher")
    public JobLauncher jobLauncher(@Qualifier("taskExecutor")TaskExecutor taskExecutor) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(taskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }



    //--------------------------------------------------------------------------------------------------------------//

    @Bean("jobImport")
    public Job jobImport(@Qualifier("taskExecutor")TaskExecutor taskExecutor,
                                  BatchImportListener listener) {
        return new JobBuilder("import_persons", jobRepository) //Name of the Job
                .incrementer(new RunIdIncrementer())
                .start(createImportStep(taskExecutor))
                .listener(listener)
                .build();
    }

    @Bean
    public Step createImportStep(@Qualifier("taskExecutor")TaskExecutor taskExecutor) {
        return new StepBuilder("chunk_import_step", jobRepository) //Name of the step
                .chunk(chunk, transactionManager)
                .reader(createImportReader(batchImportService, null, chunk, "STEP_READER_IMPORTS"))
                .processor(createDelegatedItemProcessor(batchImportService))
                .writer(createDelegatedItemWriter(batchImportService, "STEP_WRITER_IMPORT", false))
                .faultTolerant()
                .skipPolicy(new ExternalRecordVerificationSkipper())
                .taskExecutor(taskExecutor)
                .throttleLimit(importThreads)
                .build();
    }

    private ItemReader createImportReader(DelegatedItemReaderRepository delegatedItemReaderRepository, Object filter, int pageSize, String stepName) {
        return new RepositoryItemReaderAbstract(delegatedItemReaderRepository, filter, pageSize, stepName);
    }

    private ItemProcessor createDelegatedItemProcessor(DelegatedItemProcessorService delegatedItemProcessorService) {
        return new ServiceItemProcessorAbstract(delegatedItemProcessorService);
    }

    private ItemWriter createDelegatedItemWriter(DelegatedItemWriterService service, String stepName, boolean bulkSave) {
        ServiceItemWriterAbstract<?> writer = new ServiceItemWriterAbstract();
        writer.setService(service);
        writer.setStepName(stepName);
        writer.setBulkSave(bulkSave);
        return writer;
    }
}
