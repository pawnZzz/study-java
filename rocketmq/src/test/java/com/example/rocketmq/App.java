package com.example.rocketmq;

import java.util.Calendar;
import java.util.Date;

/**
 * @author zyc
 */
public class App {
    public static void main(String[] args) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + 7200 * 1000);

        System.out.println(nowDate);
        System.out.println(expireDate);
    }
}
