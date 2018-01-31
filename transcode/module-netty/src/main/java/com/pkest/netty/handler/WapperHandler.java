package com.pkest.netty.handler;

import com.pkest.netty.proto.WrapperOuterClass.Wrapper;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.GsonUtil;
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

    public T wrapperToProtocol(Wrapper wrapper) throws Exception {
        T obj = null;
        Class classType = Class.forName(wrapper.getClassType());
        switch (wrapper.getContentType()){
            case OBJECT:
                obj = (T) GsonUtil.getGson().fromJson(new String(wrapper.getContent().toByteArray()), classType);
                break;
            default:
                obj = (T) GsonUtil.getGson().fromJson("{}", classType);
                obj.setContent(wrapper.getContent().toByteArray());
        }
        obj.setContentType(wrapper.getContentType());
        obj.setClassType(wrapper.getClassType());
        return obj;
    }

    public ChannelFuture send(ChannelHandlerContext ctx, T obj) {
        return ctx.writeAndFlush(obj.toWrapper());
    }

    public ChannelFuture send(Channel channel, T obj) {
        return channel.writeAndFlush(obj.toWrapper());
    }

}
