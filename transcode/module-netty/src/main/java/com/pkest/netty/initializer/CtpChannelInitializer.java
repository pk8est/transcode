package com.pkest.netty.initializer;

import com.pkest.netty.decoder.CtpDispatcherDecoder;
import com.pkest.netty.proto.WrapperOuterClass;
import com.pkest.netty.util.ScanAnnotationUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzhonggui on 2018/2/8.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class CtpChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {

    private List<ChannelHandler> lastHandlers = new ArrayList();
    private SocketChannel socketChannel;
    private ChannelPipeline channelPipeline;

    public List<ChannelHandler> getLastHandlers() {
        return lastHandlers;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public ChannelPipeline getChannelPipeline() {
        return channelPipeline;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        socketChannel = ch;
        channelPipeline = socketChannel.pipeline();

        channelPipeline.addLast(new ProtobufVarint32FrameDecoder());
        channelPipeline.addLast(new ProtobufDecoder(WrapperOuterClass.Wrapper.getDefaultInstance()));
        channelPipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        channelPipeline.addLast(new ProtobufEncoder());
        channelPipeline.addLast(new CtpDispatcherDecoder());
        for(ChannelHandler handler: lastHandlers){
            channelPipeline.addLast(handler);
        }

    }

    public void addLast(ChannelHandler handler) {
        ScanAnnotationUtil.scanProtocolClass(handler.getClass());
        lastHandlers.add(handler);
    }
}
