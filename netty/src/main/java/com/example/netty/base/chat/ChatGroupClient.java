package com.example.netty.base.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author zyc
 */
public class ChatGroupClient {
    public String host;
    public int port;

    public ChatGroupClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());

                            pipeline.addLast(new ChatGroupClientHandler());

                        }
                    });

            ChannelFuture cf = bootstrap.connect(host, port).sync();

            Channel channel = cf.channel();
            System.out.println("=======" + channel.remoteAddress() + "===========");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                channel.writeAndFlush(scanner.nextLine()+ "\r\n");
            }
        } finally {
            clientGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ChatGroupClient("localhost", 6666).run();
    }
}
