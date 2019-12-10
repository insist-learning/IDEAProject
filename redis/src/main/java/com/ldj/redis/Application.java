package com.ldj.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource(locations= {"classpath:application-context.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
