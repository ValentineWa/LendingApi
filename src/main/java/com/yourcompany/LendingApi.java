package com.yourcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(
        exclude = {RedisRepositoriesAutoConfiguration.class},
        scanBasePackages = {"com.yourcompany"})
@Configuration
public class LendingApi
{
    public static void main( String[] args )
    {

        SpringApplication.run(LendingApi.class, args);
    }
}
