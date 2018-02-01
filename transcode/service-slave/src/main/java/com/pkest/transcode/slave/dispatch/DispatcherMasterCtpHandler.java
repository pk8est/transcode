package com.pkest.transcode.slave.dispatch;


import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.event.EventListener;
import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.protocol.CtpProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

@ChannelHandler.Sharable
public class DispatcherMasterCtpHandler extends CtpProtocolHandler {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherMasterCtpHandler.class);

    public DispatcherMasterCtpHandler(CtpBootstrap ctpBootstrap) {
        super(ctpBootstrap);
    }

    @Override
    public void active(ChannelHandlerContext ctx) throws Exception {
        logger.info("active");
    }

    @Override
    public void dispatch(ChannelHandlerContext ctx, CtpProtocol obj) throws Exception {
        List<EventListener> events = getBootstrap().getEventListeners(obj.getClass());
        for (EventListener event: events){
            event.handler(obj);
        }
    }

    @Override
    public void inactive(ChannelHandlerContext ctx) throws Exception {

    }

}
