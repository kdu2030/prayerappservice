package com.kevin.prayerappservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executor;

@TestConfiguration
public class TestExecutorConfiguration {
    @Bean
    @Primary
    public Executor testExecutor(){
        return Runnable::run;
    }
}
