package com.hungpt.myblog.config;

import com.hungpt.myblog.job.NewPostEmailJob;
import com.hungpt.myblog.job.PostViewSyncJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class JobScheduler {

    @Value("${new.article.email.job.cron.expression}")
    private String emailCronExpression;

    @Value("${post.view.sync.data.job.cron.expression}")
    private String syncDataCronExpression;

    // Job for sending emails
    @Bean
    public JobDetailFactoryBean sendEmailJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(NewPostEmailJob.class);
        factory.setDurability(true);
        factory.setName("sendEmailJob");
        factory.setDescription("Job to send emails");
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean sendEmailTrigger(@Qualifier("sendEmailJobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setCronExpression(emailCronExpression);
        factory.setName("sendEmailTrigger");
        factory.setDescription("Trigger for send email job");
        return factory;
    }

    // Job for syncing data
    @Bean
    public JobDetailFactoryBean syncDataJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(PostViewSyncJob.class);
        factory.setDurability(true);
        factory.setName("syncDataJob");
        factory.setDescription("Job to sync data");
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean syncDataTrigger(@Qualifier("syncDataJobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setCronExpression(syncDataCronExpression);
        factory.setName("syncDataTrigger");
        factory.setDescription("Trigger for sync data job");
        return factory;
    }
}