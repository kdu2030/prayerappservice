package com.kevin.prayerappservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public SimpleAsyncTaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("async-");
        executor.setVirtualThreads(true);
        return executor;
    }
}
