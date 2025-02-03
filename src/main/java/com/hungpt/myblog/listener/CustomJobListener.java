package com.hungpt.myblog.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomJobListener implements JobListener {

    @Override
    public String getName() {
        return "CustomJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job is about to be executed: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Job execution was vetoed: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        if (jobException != null) {
            log.error("Job failed with exception: ", jobException);
        } else {
            log.info("Job executed successfully: " + context.getJobDetail().getKey());
        }
    }
}
