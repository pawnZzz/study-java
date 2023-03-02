package com.example.designpattern.strategy.ifelse1;

import org.springframework.stereotype.Service;

/**
 * @author zyc
 */
@Service
public class WechatPay implements PayStrategy {
    @Override
    public PayResult pay(Order order) {
        return new PayResult(200, "微信支付成功");
    }
}
