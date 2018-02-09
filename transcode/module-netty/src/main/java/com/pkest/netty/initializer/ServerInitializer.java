package com.pkest.netty.initializer;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.exception.NettyProtocolNullException;
import com.pkest.netty.handler.DispatcherCtpHandler;
import com.pkest.netty.proto.WrapperOuterClass;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.GsonUtil;
import com.pkest.netty.util.ScanAnnotationUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.List;

/**
 * Created by wuzhonggui on 2017/2/24.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private CtpBootstrap bootstrap;

    public ServerInitializer(){
    }

    public ServerInitializer(CtpBootstrap bootstrap){
        this.bootstrap = bootstrap;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
      /*  ChannelPipeline p = ch.pipeline();
        p.addLast(new DispatcherServletInitializer());*/
        ChannelPipeline p = ch.pipeline();

        p.addLast(new ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder(WrapperOuterClass.Wrapper.getDefaultInstance()));
        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast(new ProtobufEncoder());
        //p.addLast(bootstrap.getLastHandler());
        p.addLast(new DispatcherHandler());
        p.addLast(new DispatcherCtpHandler());

    }

    class DispatcherHandler<T extends CtpProtocol> extends MessageToMessageDecoder<WrapperOuterClass.Wrapper> {

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, WrapperOuterClass.Wrapper wrapper, List<Object> out) throws Exception {
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
            out.add(wrapper);
        }

        public Class getClassType(String classType) throws ClassNotFoundException, NettyProtocolNullException {
            Class clazz = ScanAnnotationUtil.getProtocol(classType);
            if(clazz == null){
                throw new NettyProtocolNullException(classType);
            }
            return clazz;
        }
    }

}
