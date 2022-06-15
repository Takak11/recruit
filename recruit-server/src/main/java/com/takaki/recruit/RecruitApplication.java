package com.takaki.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Takaki
 */
@EnableAsync
@SpringBootApplication
public class RecruitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class, args);
    }
}
