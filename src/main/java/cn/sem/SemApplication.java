package cn.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.sem"})
@EnableReactiveMongoRepositories(basePackages = {"cn.sem.repository"})
public class SemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemApplication.class, args);
    }
}
