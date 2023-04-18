package com.example.netty.base.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zyc
 */
public class ChatGroupClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        ctx.channel().writeAndFlush(msg);
        System.out.println(msg.trim());
    }
}
