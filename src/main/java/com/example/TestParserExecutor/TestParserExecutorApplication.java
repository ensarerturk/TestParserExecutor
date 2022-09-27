package com.example.TestParserExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com"})
public class TestParserExecutorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestParserExecutorApplication.class, args);
    }
}
