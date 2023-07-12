package com.example.rocketmq;

import com.example.rocketmq.spring.DataChangeTypeEnum;
import com.example.rocketmq.spring.FCrmRocketmqMessage;
import com.example.rocketmq.spring.MyListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zyc
 * @description: TODO
 * @date: 2023/6/6
 */
@SpringBootTest(classes = RocketmqApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private DefaultMQPushConsumer rocketMQConsumer;

    @Test
    public void send(){
        FCrmRocketmqMessage<String> msg = new FCrmRocketmqMessage<>();
        msg.setData("test");
        msg.setType("api-name");
        msg.setOp(DataChangeTypeEnum.INCR);
        msg.setStatus("success");
        msg.setLastModifiedTime(123634675463L);
        rocketMQTemplate.convertAndSend("spb-demo-t", msg);
    }

    @Test
    public void consumer() throws MQClientException {

    }
}
