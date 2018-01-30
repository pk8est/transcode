package com.pkest.netty.initializer;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.proto.WrapperOuterClass;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
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
public class DispatcherServletInitializer  extends ByteToMessageDecoder {

    private final CtpBootstrap bootstrap;

    public DispatcherServletInitializer(CtpBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Will use the first five bytes to detect a protocol.
        if (in.readableBytes() < 5) {
            return;
        }
        final int magic1 = in.getUnsignedByte(in.readerIndex());
        final int magic2 = in.getUnsignedByte(in.readerIndex() + 1);
        ChannelPipeline p = ctx.pipeline();
        if (isHttp(magic1, magic2)) {
            /*p.addLast(new HttpRequestDecoder());
            p.addLast(new HttpResponseEncoder());
            p.addLast(new HttpObjectAggregator(65536));
            p.addLast(new ChunkedWriteHandler());
            p.addLast(bootstrap.getHttpServletAdapter().addLastHandler(dispatcherServlet));*/
        } else {
            p.addLast(new ProtobufVarint32FrameDecoder());
            p.addLast(new ProtobufDecoder(WrapperOuterClass.Wrapper.getDefaultInstance()));
            p.addLast(new ProtobufVarint32LengthFieldPrepender());
            p.addLast(new ProtobufEncoder());
            p.addLast(bootstrap.getLastCtpAdapter().addLastHandler(bootstrap));
        }
        p.remove(this);
    }

    private static boolean isHttp(int magic1, int magic2) {
        return
                magic1 == 'G' && magic2 == 'E' || // GET
                        magic1 == 'P' && magic2 == 'O' || // POST
                        magic1 == 'P' && magic2 == 'U' || // PUT
                        magic1 == 'H' && magic2 == 'E' || // HEAD
                        magic1 == 'O' && magic2 == 'P' || // OPTIONS
                        magic1 == 'P' && magic2 == 'A' || // PATCH
                        magic1 == 'D' && magic2 == 'E' || // DELETE
                        magic1 == 'T' && magic2 == 'R' || // TRACE
                        magic1 == 'C' && magic2 == 'O';   // CONNECT
    }
}
