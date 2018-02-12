package com.pkest.transcode.master.handler;

import com.pkest.transcode.common.protocol.LoginProtocol;
import com.pkest.transcode.master.ram.NettyClientRam;
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
        NettyClientRam.add(ctx);
        logger.info("LoginHandler channelActive!");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LoginProtocol message) throws Exception {
        logger.info("Master LoginHandler messageReceived: " + message.getChannelId());
        ctx.writeAndFlush(new LoginProtocol("master login response!").toWrapper());
    }

}
