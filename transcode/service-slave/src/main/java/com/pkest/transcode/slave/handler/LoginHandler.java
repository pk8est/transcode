package com.pkest.transcode.slave.handler;

import com.pkest.transcode.common.protocol.LoginProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzhonggui on 2018/2/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginProtocol> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Client LoginHandler channelActive!");
        ctx.writeAndFlush(new LoginProtocol("slave login request!").toWrapper());
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LoginProtocol message) throws Exception {
        logger.info("Client LoginHandler messageReceived: " + message.getChannelId());
    }
}
