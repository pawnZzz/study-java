package com.example.designpattern.strategy.ifelse2;

/**
 * @author zyc
 */
public class ExternalTaxStrategy implements ChargeStrategy{
    @Override
    public double charge(long cost) {
        return cost;
    }
}
