package com.hungpt.myblog.schedule;

import com.hungpt.myblog.job.PostViewSyncJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class PostViewSyncDataSchedule {
    @Value("${post.view.sync.data.job.cron.expression}")
    private String syncDataCronExpression;

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

    // Trigger for syncing data
    @Bean
    public CronTriggerFactoryBean syncDataTrigger(@Qualifier("syncDataJobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setCronExpression(syncDataCronExpression);
        factory.setName("syncDataTrigger");
        factory.setDescription("Trigger for syncing data job");
        return factory;
    }
}
