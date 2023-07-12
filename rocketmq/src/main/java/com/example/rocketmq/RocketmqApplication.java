package com.example.rocketmq;

import com.example.rocketmq.spring.ConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author zyc
 * @description: TODO
 * @date: 2023/6/6
 */
@SpringBootApplication
@Import(value = ConsumerConfig.class)
public class RocketmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication.class, args);
    }
}
