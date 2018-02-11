package com.pkest.netty.decoder;

import com.pkest.netty.exception.NettyProtocolNullException;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.GsonUtil;
import com.pkest.netty.util.ScanAnnotationUtil;
import com.pkest.netty.proto.WrapperOuterClass;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by wuzhonggui on 2018/2/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class CtpDispatcherDecoder<T extends CtpProtocol> extends MessageToMessageDecoder<WrapperOuterClass.Wrapper> {

    @Override
    protected void decode(ChannelHandlerContext ctx, WrapperOuterClass.Wrapper wrapper, List<Object> out) throws Exception {
        T obj;
        Class clazz = getClassType(wrapper.getClassType());
        switch (wrapper.getContentType()){
            case OBJECT:
                obj = (T) GsonUtil.getGson().fromJson(new String(wrapper.getContent().toByteArray()), clazz);
                break;
            default:
                obj = (T) GsonUtil.getGson().fromJson("{}", clazz);
                obj.setContent(wrapper.getContent().toByteArray());
        }
        out.add(obj);
        ctx.flush();
    }

    public Class getClassType(String classType) throws ClassNotFoundException, NettyProtocolNullException {
        Class clazz = ScanAnnotationUtil.getProtocol(classType);
        if(clazz == null){
            clazz = Class.forName(classType);
            //throw new NettyProtocolNullException(classType);
        }
        return clazz;
    }
}
