package com.example.designpattern.strategy.ifelse1;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zyc
 */
@Data
@Accessors(chain = true)
public class Order {
    /**
     * 金额
     */
    private int amount;

    /**
     * 支付类型
     */
    private String type;
}
