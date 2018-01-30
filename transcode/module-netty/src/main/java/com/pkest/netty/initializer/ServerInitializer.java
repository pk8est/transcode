package com.pkest.netty.initializer;

import com.pkest.netty.CtpBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuzhonggui on 2017/2/24.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(ServerInitializer.class);

    private final CtpBootstrap bootstrap;

    public ServerInitializer(CtpBootstrap bootstrap){
        this.bootstrap = bootstrap;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new DispatcherServletInitializer(bootstrap));
    }

}
