package com.example.netty.base.chat;

import io.netty.channel.*;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zyc
 */
public class ChatGroupServerHandler extends SimpleChannelInboundHandler<String> {

    public static DefaultChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // first run
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("--[客户端]" +channel.remoteAddress() + "加入群聊: " + sdf.format(new Date()) + "\n");
        channelGroup.add(channel); // 放write后面自己不接收上面的这个消息
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        channelGroup.remove(channel); // 先把自己去掉，不接收下面的消息
        channelGroup.writeAndFlush("--[客户端]" +channel.remoteAddress() + "退出群聊: " + sdf.format(new Date()) + "\n");
        channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("++" + ctx.channel().remoteAddress() + "上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("++" + ctx.channel().remoteAddress() + "下线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (!channel.equals(ch)) {
                ch.writeAndFlush("--[客户端]" + channel.remoteAddress() + " 发送了消息：" + msg + "\n");
            } else {
                ch.writeAndFlush("--[自己]发送了消息：" + msg + "\n");
            }
        });
    }
}
