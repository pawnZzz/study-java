package com.example.designpattern.strategy.ifelse1;

import org.springframework.stereotype.Service;

/**
 * @author zyc
 */
@Service
public class AliPay implements PayStrategy {
    @Override
    public PayResult pay(Order order) {
        return new PayResult(200, "支付宝支付成功");
    }
}
