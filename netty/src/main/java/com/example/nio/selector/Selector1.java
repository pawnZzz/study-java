package com.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zyc
 */
public class Selector1 {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 创建selector
        Selector selector = Selector.open();
        // serverSocketChannel绑定端口
//        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.bind(new InetSocketAddress(9999));

        // channel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true){
            if (selector.select(1000) == 0) { // select()方法是监听有事件的channel的个数
                System.out.println("服务器等待1s，无连接");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();  // 获取有事件的selectorKey
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // 第一次应该是上面注册的accept操作状态
                if (key.isAcceptable()) {
                    System.out.println("有新的客户端连接");
                    // serverSocketChannel连接客户端，如果多个客户端来连接时，此时怎么确定accept()的是哪个客户端的连接？
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);

                    // 连接上之后，注册read操作
                    channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // read状态的事件读取数据，本案例中，下一轮keys才会满足，此轮刚注册所以不会有
                if (key.isReadable()) {
                    // 通过Key反向获取channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取该channel对应的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();

                    channel.read(buffer);
                    System.out.println("from 客户端： " + new String(buffer.array()));
                }

                keyIterator.remove();
            }
        }
    }
}
