package com.pkest.netty.handler;


import com.esotericsoftware.reflectasm.MethodAccess;
import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.event.NettyEventHandler;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.CommonUtil;
import com.pkest.netty.util.ScanAnnotationUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

@ChannelHandler.Sharable
public class DispatcherCtpHandler extends CtpProtocolHandler {

    public DispatcherCtpHandler() {

    }

    public DispatcherCtpHandler(CtpBootstrap ctpBootstrap) {
        super(ctpBootstrap);
    }

    @Override
    public void active(ChannelHandlerContext ctx) throws Exception {
        System.err.println("DispatcherCtpHandler active");
    }

    @Override
    public void dispatch(ChannelHandlerContext ctx, CtpProtocol obj) throws Exception {
        String classType = CommonUtil.getProtocolAnnotationOrClassName(obj.getClass());
        List<NettyEventHandler> events = ScanAnnotationUtil.getEventHandler(classType);
        for (NettyEventHandler event: events){
            event.handler(ctx, obj);
        }
        List<Class> clazzs = ScanAnnotationUtil.getEventHandlerClass(classType);
        for (Class clazz: clazzs){
            MethodAccess access = MethodAccess.get(clazz);
            access.invoke(clazz.newInstance(), "handler", ctx, obj);
        }
    }

    @Override
    public void inactive(ChannelHandlerContext ctx) throws Exception {

    }

}
