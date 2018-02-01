package com.pkest.netty.handler;

import com.pkest.netty.exception.ProtocolNullException;
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

    public Class<T> getClassType(Wrapper wrapper) throws ClassNotFoundException, ProtocolNullException {
        String name = wrapper.getClassType();
        Class<T> clazz = ScanAnnotationUtil.getProtocol(name);
        if(clazz == null){
            throw new ProtocolNullException(name);
        }
        return clazz;
    }

    public T wrapperToProtocol(Wrapper wrapper) throws Exception {
        T obj = null;
        Class classType = getClassType(wrapper);
        switch (wrapper.getContentType()){
            case OBJECT:
                obj = (T) GsonUtil.getGson().fromJson(new String(wrapper.getContent().toByteArray()), classType);
                break;
            default:
                obj = (T) GsonUtil.getGson().fromJson("{}", classType);
                obj.setContent(wrapper.getContent().toByteArray());
        }
        obj.setContentType(wrapper.getContentType());
        obj.setClassType(classType.getName());
        return obj;
    }

    public ChannelFuture send(ChannelHandlerContext ctx, T obj) {
        return ctx.writeAndFlush(obj.toWrapper());
    }

    public ChannelFuture send(Channel channel, T obj) {
        return channel.writeAndFlush(obj.toWrapper());
    }

}
