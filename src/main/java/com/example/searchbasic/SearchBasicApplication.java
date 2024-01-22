package com.example.searchbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SearchBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchBasicApplication.class, args);
    }

}
