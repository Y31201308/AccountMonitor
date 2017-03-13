package com.account.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.account.monitor"})
public class AMSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(AMSpringBootApplication.class, args);
    }
}
