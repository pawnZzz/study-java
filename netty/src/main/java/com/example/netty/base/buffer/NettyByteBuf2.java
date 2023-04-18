package com.example.netty.base.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author zyc
 */
public class NettyByteBuf2 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", StandardCharsets.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            System.out.println("data: " + new String(array, StandardCharsets.UTF_8));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println("arrayOffset: " + byteBuf.arrayOffset());
            System.out.println("readerIndex: " + byteBuf.readerIndex());
            System.out.println("capacity: " + byteBuf.capacity());
            System.out.println("getByte: " + (char)byteBuf.getByte(0));

            System.out.println("readableBytes: " + byteBuf.readableBytes());

            for (int i=0;i<byteBuf.capacity();i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            //按照某个范围读取
            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
            System.out.println(byteBuf.getCharSequence(4, 6, StandardCharsets.UTF_8));
        }
    }
}
