package com.example.designpattern.strategy.ifelse1;

public enum PayTypeEnum {
    ALI_PAY(1, "aliPay"),
    WECHAT_PAY(2, "wechatPay"),
    UNION_PAY(3, "unionPay"),
    ;

    private int key;

    private String value;

    PayTypeEnum(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static PayTypeEnum keyOf(Integer key){
        if (null == key){
            return null;
        }
        for (PayTypeEnum ayTypeEnum : values()) {
            if (ayTypeEnum.getKey().equals(key)){
                return ayTypeEnum;
            }
        }
        return null;
    }
}
