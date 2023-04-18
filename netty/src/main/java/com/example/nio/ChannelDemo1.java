package com.example.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zyc
 */
public class ChannelDemo1 {
    public static void main(String[] args) throws IOException {
        String str = "Hello World";
        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/zhangyuncheng/Downloads/个人/netty-channel.txt");

        FileChannel channel = fileOutputStream.getChannel();

        // 创建一个缓存
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(str.getBytes());

        // 进行flip翻转操作 position limit反转
        buffer.flip();

        channel.write(buffer);

        fileOutputStream.close();
    }
}
