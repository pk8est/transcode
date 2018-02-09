package com.pkest.transcode.master;

import com.pkest.netty2.initializer.CtpChannelInitializer;
import com.pkest.transcode.master.handler.Login2Handler;
import com.pkest.transcode.master.handler.LoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/2/6.
 */
public class MasterBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(MasterBootstrap.class);

    public static void main(String[] args) {

        runServer();

        /*final CtpServerBootstrap server = new CtpServerBootstrap(9001, "127.0.0.1");

        server.setCallbackEvent(new NettyCallbackEvent() {
            @Override
            public void success(CtpBootstrap bootstrap) {
                logger.info("connect server successed!");
            }

            @Override
            public void fail(Exception e, CtpBootstrap bootstrap) {
                logger.info("connect server fail!");
            }
        });

        server.addEventListener(LoginProtocol.class, new NettyEventHandler<LoginProtocol>() {
            @Override
            public void handler(ChannelHandlerContext ctx, LoginProtocol message) {
                System.err.println("master. " + message.getChannelId());
                server.send(ctx.channel(), new LoginProtocol("response1"));
                ctx.writeAndFlush((new LoginProtocol("response2")).toWrapper());
            }
        });
        server.start();*/
    }

    public static void runServer(){
        EventLoopGroup boss;
        EventLoopGroup worker;
        ServerBootstrap bootstrap;

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);  //连接数
            bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);  //长连接

            CtpChannelInitializer initializer = new CtpChannelInitializer();
            initializer.addLast(new Login2Handler());
            initializer.addLast(new LoginHandler());
            bootstrap.childHandler(initializer);
            /*bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();

                    p.addLast(new ProtobufVarint32FrameDecoder());
                    p.addLast(new ProtobufDecoder(WrapperOuterClass.Wrapper.getDefaultInstance()));
                    p.addLast(new ProtobufVarint32LengthFieldPrepender());
                    p.addLast(new ProtobufEncoder());
                    p.addLast(new CtpDispatcherDecoder());
                    p.addLast(new Login2Handler());
                    p.addLast(new LoginHandler());
                }
            });*/
            ChannelFuture future = bootstrap.bind(9001);
            future.addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    System.err.println("server run success!");
                }
            });

        }catch(Exception e){

        }
    }

}
