package com.pkest.transcode.slave.dispatch;


import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.transcode.common.protocol.LoginProtocol;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

public class DispatcherMasterCtpHandler extends CtpProtocolHandler {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherMasterCtpHandler.class);

    public DispatcherMasterCtpHandler(CtpBootstrap ctpBootstrap) {
        super(ctpBootstrap);
    }

    @Override
    public void active(ChannelHandlerContext ctx) throws Exception {
        logger.info("active");
        send(ctx, new LoginProtocol(getChannelId()));
    }

    @Override
    public void dispatch(ChannelHandlerContext ctx, CtpProtocol obj) throws Exception {
        logger.info("dispatch");
    }

    @Override
    public void inactive(ChannelHandlerContext ctx) throws Exception {

    }

}
