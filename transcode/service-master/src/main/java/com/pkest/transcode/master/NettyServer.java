package com.pkest.transcode.master;

import com.pkest.netty2.initializer.CtpChannelInitializer;
import com.pkest.transcode.master.handler.Login2Handler;
import com.pkest.transcode.master.handler.LoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * Created by wuzhonggui on 2018/2/9.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@Component
public class NettyServer {

    public void start() {
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
            bootstrap.bind(9001);
        }catch(Exception e){

        }
    }
}
