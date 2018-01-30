package com.pkest.netty.initializer;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.proto.WrapperOuterClass;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    final CtpBootstrap bootstrap;
    public ClientInitializer(CtpBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder(WrapperOuterClass.Wrapper.getDefaultInstance()));
        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast(new ProtobufEncoder());
        p.addLast(bootstrap.getLastCtpAdapter().addLastHandler(bootstrap));
    }

}
