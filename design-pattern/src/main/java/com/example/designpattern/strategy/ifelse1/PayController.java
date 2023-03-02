package com.example.designpattern.strategy.ifelse1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyc
 */
@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class PayController {


    /**
     * 支付API
     *
     * @param amount  金额
     * @param payType 支付类型
     * @return PayResult
     */
    @GetMapping("pay")
    public PayResult pay(int amount, int payType) {
        /**
         * !!! 通过@Service注解将支付实现类注入spring工厂管理，默认bean名字是类名（开头小写）
         *      或者@Component("bean名字")
         */
        String payTypeName = PayTypeEnum.keyOf(payType).getValue();
        Order order = new Order().setAmount(amount).setType(payTypeName);

        //  根据【支付类型】获取对应的策略 bean
        PayStrategy payment = SpringContextUtil.getBean(payTypeName, PayStrategy.class);

        //  开始调用策略对应的支付业务逻辑
        return payment.pay(order);
    }

}
