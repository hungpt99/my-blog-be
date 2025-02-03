package com.hungpt.myblog.schedule;

import com.hungpt.myblog.job.NewPostEmailJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class NewPostEmailSchedule {
    @Value("${new.article.email.job.cron.expression}")
    private String emailCronExpression;

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

    // Trigger for sending emails
    @Bean
    public CronTriggerFactoryBean sendEmailTrigger(@Qualifier("sendEmailJobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setCronExpression(emailCronExpression);
        factory.setName("sendEmailTrigger");
        factory.setDescription("Trigger for sending email job");
        return factory;
    }
}
