package com.example.designpattern.strategy.ifelse2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zyc
 */
public class AnnotationChargeStrategyFactory {
    /**
     * 存储策略
     */
    static Map<ChargeType, ChargeStrategy> chargeStrategyMap = new HashMap<>();

    static {
        registerChargeStrategy();
    }

    /**
     *     通过map获取策略，当增加新的策略时无需修改代码
     */
    public static ChargeStrategy getChargeStrategy(ChargeType chargeType) throws Exception {
        // 当增加新的类型时，需要修改代码
        if (chargeStrategyMap.containsKey(chargeType)) {
            return chargeStrategyMap.get(chargeType);
        } else {
            throw new Exception("未配置相应策略");
        }
    }

    /**
     * 自动注册策略
     */

    private static void registerChargeStrategy() {/*
        // 通过反射找到所有的策略子类进行注册
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(ChargeStrategy.class.getPackage().getName()))
                .setScanners(new SubTypesScanner()));
        Set<Class<? extends ChargeStrategy>> taxStrategyClassSet = reflections.getSubTypesOf(ChargeStrategy.class);

        if (taxStrategyClassSet != null) {
            for (Class<?> clazz : taxStrategyClassSet) {
                // 找到类型注解，自动完成策略注册
                if (clazz.isAnnotationPresent(ChargeTypeAnnotation.class)) {
                    ChargeTypeAnnotation taxTypeAnnotation = clazz.getAnnotation(ChargeTypeAnnotation.class);
                    ChargeType chargeType = taxTypeAnnotation.taxType();
                    try {
                        chargeStrategyMap.put(chargeType, (ChargeStrategy) clazz.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.getStackTrace();
                    }
                }
            }
        }*/
    }
}
