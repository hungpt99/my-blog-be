package com.hungpt.myblog.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewPostEmailJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Logic to send email for new post
        System.out.println("New post email notification sent!");
    }
}