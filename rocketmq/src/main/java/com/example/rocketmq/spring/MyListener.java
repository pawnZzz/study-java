package com.example.rocketmq.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zyc
 * @description: TODO
 * @date: 2023/6/6
 */
/*
@Component
@RocketMQMessageListener(consumerGroup = "spb_demo_consumer", topic = "spb-demo-t" )
@Slf4j
public class MyListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("----msg: " + message);
    }
}
*/

public class MyListener{

}
