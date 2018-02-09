package com.pkest.transcode.slave;

import com.pkest.netty2.initializer.CtpChannelInitializer;
import com.pkest.transcode.slave.handler.Login2Handler;
import com.pkest.transcode.slave.handler.LoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/2/6.
 */
public class SlaveBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(SlaveBootstrap.class);

    public static void main(String[] args) throws IOException {
        EventLoopGroup group;
        Bootstrap bootstrap;

        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        try {
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(group);
            CtpChannelInitializer initializer = new CtpChannelInitializer();
            initializer.addLast(new LoginHandler());
            initializer.addLast(new Login2Handler());
            bootstrap.handler(initializer);

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9001));
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        /*final CtpClientBootstrap client = new CtpClientBootstrap(9001, "127.0.0.1");
        client.setCallbackEvent(new NettyCallbackEvent() {
            @Override
            public void success(CtpBootstrap bootstrap) {
                logger.info("connect server successed!");
                //handler.send(client.getChannel(), new LoginProtocol("handsome wu"));
                client.send(new LoginProtocol("handsome wu"));
            }

            @Override
            public void fail(Exception e, CtpBootstrap bootstrap) {
                logger.info("connect server fail!");
            }
        });
        client.addEventListener(LoginProtocol.class, new NettyEventHandler<LoginProtocol>() {
            @Override
            public void handler(ChannelHandlerContext ctx, LoginProtocol message) {
                System.err.println("1. " + message.getChannelId());
            }
        });
        client.addEventListener(new NettyEventHandler<LoginProtocol>() {
            @Override
            public void handler(ChannelHandlerContext ctx, LoginProtocol message) {
                System.err.println("2. " + message.getChannelId());
            }
        });
        client.start();*/
    }

}
