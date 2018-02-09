package com.pkest.netty.handler;

import com.pkest.netty.exception.NettyProtocolNullException;
import com.pkest.netty.proto.WrapperOuterClass.Wrapper;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.GsonUtil;
import com.pkest.netty.util.ScanAnnotationUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wuzhonggui on 2017/2/16.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class WapperHandler<T extends CtpProtocol> extends SimpleChannelInboundHandler<Wrapper>{

    protected String channelId = "";
    abstract public void dispatch(ChannelHandlerContext ctx, T obj) throws Exception;

    @Override
    public final void messageReceived(ChannelHandlerContext ctx, Wrapper wrapper) throws Exception {
        dispatch(ctx, wrapperToProtocol(wrapper));
    }

    public Class<T> getClassType(String classType) throws ClassNotFoundException, NettyProtocolNullException {
        Class<T> clazz = ScanAnnotationUtil.getProtocol(classType);
        if(clazz == null){
            throw new NettyProtocolNullException(classType);
        }
        return clazz;
    }

    public T wrapperToProtocol(Wrapper wrapper) throws Exception {
        T obj = null;
        String classType = wrapper.getClassType();
        Class clazz = getClassType(classType);
        switch (wrapper.getContentType()){
            case OBJECT:
                obj = (T) GsonUtil.getGson().fromJson(new String(wrapper.getContent().toByteArray()), clazz);
                break;
            default:
                obj = (T) GsonUtil.getGson().fromJson("{}", clazz);
                obj.setContent(wrapper.getContent().toByteArray());
        }
        obj.setContentType(wrapper.getContentType());
        obj.setClassType(classType);
        return obj;
    }

    public ChannelFuture send(ChannelHandlerContext ctx, T obj) {
        return ctx.writeAndFlush(obj.toWrapper());
    }

    public ChannelFuture send(Channel channel, T obj) {
        return channel.writeAndFlush(obj.toWrapper());
    }

}
