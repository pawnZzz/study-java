package com.example.designpattern.strategy.ifelse2;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ChargeTypeAnnotation {
    ChargeType taxType();
}
