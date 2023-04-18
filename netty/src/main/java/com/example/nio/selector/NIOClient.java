package com.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zyc
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);

        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要事件，此间我们干点什么...");
            }
        }

        String str = "Hello World";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(buffer);

        System.in.read();

    }
}
