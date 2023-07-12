package com.example.rocketmq.spring;

/**
 *  消息队列 数据变更类型
 * @author zyc
 */
public enum DataChangeTypeEnum {
    INCR("+I","新增"),
    UPDATE_BEFORE("-U","更新前"),
    UPDATE_AFTER("+U","更新后"),
    DELETE("-D","删除"),

    ;


    private String type;
    private String describe;

    DataChangeTypeEnum(String name, String describe) {
        this.type = name;
        this.describe = describe;
    }

    public static String getNameByValue(String value) {
        DataChangeTypeEnum[] changeTypeEnums = values();
        for (DataChangeTypeEnum changeTypeEnum : changeTypeEnums) {
            if (changeTypeEnum.getDescribe().equals(value)) {
                return changeTypeEnum.getType();
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }
}
