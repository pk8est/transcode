package com.pkest.netty;

import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.handler.LastCtpAdapter;
import com.pkest.netty.initializer.ClientInitializer;
import com.pkest.netty.util.StringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuzhonggui on 2017/2/24.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class CtpClientBootstrap extends CtpBootstrap {
    protected int port;
    protected String host;
    protected EventLoopGroup group;
    protected Bootstrap bootstrap;
    protected ChannelHandler ctpHandler;
    private static final Logger logger = LoggerFactory.getLogger(CtpClientBootstrap.class);

    public CtpClientBootstrap(int port, String host){
        this.port = port;
        this.host = host;
        setLastCtpAdapter(new LastCtpAdapter() {
            @Override
            public CtpProtocolHandler addLastHandler(CtpBootstrap bootstrap) {
                return new CtpProtocolHandler(bootstrap);
            }
        });
        init();
        setChannelId(StringUtil.getUUID32());
    }

    @Override
    public void setChannelHandler() {
        bootstrap.handler(new ClientInitializer(this));
    }

    public void setOption(Bootstrap bootstrap){
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
    }

    public void init(){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        setOption(bootstrap);
        bootstrap.group(group);
        bootstrap.remoteAddress(host, port);
        setChannelHandler();
    }

    public void start() {
        if (closed){
            return;
        }
        try{
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
            future.addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if(f.isSuccess()){
                        channel = f.channel();
                        startSuccessed();
                        unsetAgainCount();
                    }else if(againCount > 0){
                        logger.error("第 {} 次重连, {}:{}", againCount, host, port);
                        TimeUnit.SECONDS.sleep(againInterval);
                        start();
                        againCount--;
                    }else{
                        startFailed(new RuntimeException("连接远程服务失败"));
                        stop();
                    }
                }
            });
        }catch (Exception e){
            startFailed(e);
            stop();
        }
    }

    public void stop(){
        if (null != group) {
            group.shutdownGracefully();
        }
    }

}