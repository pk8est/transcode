package com.pkest.transcode.slave;

import com.pkest.netty.initializer.CtpChannelInitializer;
import com.pkest.transcode.slave.handler.LoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by wuzhonggui on 2018/2/9.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Component
public class NettyClient {

    @Value("${master.netty.port}")
    private int port = 9001;

    @Value("${master.netty.ip}")
    private String ip = "127.0.0.1";

    public void start() {
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
            bootstrap.handler(initializer);

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(ip, port));
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //group.shutdownGracefully();
        }
    }
}
