package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 多buffer使用
 * @author zyc
 */
public class ScatteringGathering {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int maxLen = 8;

        while (true) {
            int readLen = 0;
            while (readLen < maxLen) {
                long l = socketChannel.read(byteBuffers);
                readLen += l;
                System.out.println("readLen: " + readLen);
                Arrays.stream(byteBuffers)
                        .map(buffer -> "read -- position: " + buffer.position() + " limit: " + buffer.limit())
                        .forEach(System.out::println);
            }
            Arrays.stream(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            int writeLen = 0;
            while (writeLen < maxLen) {
                long write = socketChannel.write(byteBuffers);
                writeLen += write;
            }

            System.out.println("readLen: " + readLen + " writeLen: " + writeLen + " maxLen: " + maxLen);

        }

    }
}
