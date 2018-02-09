package com.pkest.transcode.master.handler;

import com.pkest.transcode.common.protocol.Login2Protocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wuzhonggui on 2018/2/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@ChannelHandler.Sharable
public class Login2Handler extends SimpleChannelInboundHandler<Login2Protocol> {

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("Login2Handler channelActive");
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Login2Protocol message) throws Exception {
        System.err.println("Login2Handler messageReceived " + message.getChannelId());

    }
}
