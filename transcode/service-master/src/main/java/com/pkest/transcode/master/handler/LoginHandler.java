package com.pkest.transcode.master.handler;

import com.pkest.transcode.common.protocol.Login2Protocol;
import com.pkest.transcode.common.protocol.LoginProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wuzhonggui on 2018/2/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginProtocol> {

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("LoginHandler channelActive");
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LoginProtocol message) throws Exception {
        System.err.println("LoginHandler messageReceived " + message.getChannelId());
        ctx.writeAndFlush(new Login2Protocol("master login response!").toWrapper());
    }
}