package com.example.designpattern.strategy.ifelse1;

public interface PayStrategy {
    /**
     * 支付
     *
     * @param order 订单信息
     * @return PayResult 支付结果
     */
    PayResult pay(Order order);
}
