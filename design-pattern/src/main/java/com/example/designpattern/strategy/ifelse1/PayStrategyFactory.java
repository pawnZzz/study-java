package com.example.designpattern.strategy.ifelse1;

import org.springframework.stereotype.Component;

/**
 * @author zyc
 */
public class PayStrategyFactory {
    /**
     * 通过渠道码获取支付策略具体实现类
     */
    public static PayStrategy getPayStrategy(Integer channel) {
        PayTypeEnum payEnum = PayTypeEnum.keyOf(channel);
        if (payEnum == null) {
            return null;
        }
        return SpringContextUtil.getBean(payEnum.getValue(), PayStrategy.class);
    }
}
