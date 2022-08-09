package com.coding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author felix
 */
@Slf4j
@EnableScheduling
@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class FoodApp {


    public static void main(String[] args) {
        SpringApplication.run(FoodApp.class, args);
    }


}
