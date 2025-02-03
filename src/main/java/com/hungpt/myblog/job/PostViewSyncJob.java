package com.hungpt.myblog.job;

import com.hungpt.myblog.redis.RedisWrapper;
import com.hungpt.myblog.repository.PostRepository;
import com.hungpt.myblog.service.PostViewSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostViewSyncJob implements Job {

    private final PostViewSyncService postViewSyncService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        postViewSyncService.syncViewCounts();
    }

}
