package com.example.netty.base.dubbo;

/**
 * @author zyc
 */
public class SBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
