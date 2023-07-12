package com.example.rocketmq.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class App {
    public static void main(String[] args) throws JsonProcessingException, MQClientException {
       /* FCrmQueryInfoParam queryInfoParam = new FCrmQueryInfoParam();

        FCrmQueryDataParam data = new FCrmQueryDataParam();

        Long milliSeconds= TimeUnit.MILLISECONDS.convert(Integer.valueOf(100).longValue(), TimeUnit.DAYS);
        Long dateTime = System.currentTimeMillis() - milliSeconds;
        FCrmSearchFilter filter = new FCrmSearchFilter();
        filter.setField_name("last_modified_time");
        filter.setField_values(dateTime.toString());
        System.out.println("----" + dateTime.toString());
        filter.setOperator("GTE");
        queryInfoParam.setFilters(Lists.newArrayList(filter));

        queryInfoParam.setLimit(10);
        FCrmOrder orders = new FCrmOrder();
        orders.setFieldName("create_time");
        orders.setIsAsc(true);

        queryInfoParam.setOrders(Arrays.asList(orders));
        data.setDataObjectApiName("object_n9enD__c");
        queryInfoParam.setOffset(0);
        data.setSearchQueryInfo(queryInfoParam);


        FCrmSearchQueryRequest request=new FCrmSearchQueryRequest();

        request.setCorpId("FSCID_4D6137399017C216D510CE2B653526A5");
        request.setCorpAccessToken("6DA35C781DD52DB07BC754EFE16E597D");
        request.setCurrentOpenUserId("FSUID_E483DD86FEED3A77280E35C429929110");
        request.setData(data);
        request.setDataObjectApiName(data.getDataObjectApiName());


        System.out.println(JSON.toJSONString(request));*/
/*

        FCrmRocketmqMessage<String> msg = new FCrmRocketmqMessage<>();
        msg.setData("test");
        msg.setType("api-name");
        msg.setOp(DataChangeTypeEnum.INCR);
        msg.setStatus("success");
        msg.setLastModifiedTime(123634675463L);

        ObjectMapper mapper = new ObjectMapper();
        String json =  mapper.writeValueAsString(msg);
        System.out.println(json);
*/

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("spb_demo_consumer_1_1");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("spb-demo-t", "*");
        // 设置消费偏移量，从最小偏移量开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置消息消费的监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    // 处理收到的消息
                    System.out.println("---msg: " + new String(msg.getBody()));
                }

                // 返回消费状态
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者
        consumer.start();

    }
}
