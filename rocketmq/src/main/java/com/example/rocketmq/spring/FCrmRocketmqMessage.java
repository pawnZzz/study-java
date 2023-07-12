package com.example.rocketmq.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * fcrm数据同步到rocketmq 消息
 * @author zyc
 */
@Data
public class FCrmRocketmqMessage<T> {
    private String type; // 数据类型，api_name
    private DataChangeTypeEnum op; // +I:新增, +U:更新, -D: 删除
    private T data; // 具体数据
    private String status; // 申请单、数据状态
    // 最后操作时间
    @JsonProperty(value = "last_modified_time")
    private Long lastModifiedTime;
}
