package com.hungpt.myblog.config;

import com.hungpt.myblog.listener.CustomJobListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Slf4j
@Configuration
@Import(JobScheduler.class)
public class QuartzConfig {

    @Value("${org.quartz.scheduler.instanceName}")
    private String instanceName;

    @Value("${org.quartz.scheduler.instanceId}")
    private String instanceId;

    @Value("${org.quartz.threadPool.threadCount}")
    private String threadCount;

    @Autowired
    @Qualifier("sendEmailTrigger")
    private CronTriggerFactoryBean sendEmailTrigger;

    @Autowired
    @Qualifier("syncDataTrigger")
    private CronTriggerFactoryBean syncDataTrigger;

    // Bean to register the JobListener
    @Bean
    public JobListener customJobListener() {
        return new CustomJobListener();
    }

    @Bean
    public org.quartz.spi.JobFactory jobFactory(ApplicationContext applicationContext) {
        QuartzJobFactory jobFactory = new QuartzJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory(applicationContext));

        // Register the JobListener
        factory.setGlobalJobListeners(customJobListener());

        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.instanceName", instanceName);
        quartzProperties.setProperty("org.quartz.scheduler.instanceId", instanceId);
        quartzProperties.setProperty("org.quartz.threadPool.threadCount", threadCount);

        factory.setQuartzProperties(quartzProperties);
        factory.setTriggers(
                sendEmailTrigger.getObject(),
                syncDataTrigger.getObject()
        );

        return factory;
    }
}
