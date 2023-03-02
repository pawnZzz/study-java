package com.example.designpattern.strategy.ifelse2;

/**
 * @author zyc
 */
public class InternalStrategy implements ChargeStrategy{
    @Override
    public double charge(long cost) {
        final double taxRate = 0.2;
        return cost * taxRate;
    }
}
