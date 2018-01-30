package com.pkest.netty;

import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.handler.LastCtpAdapter;
import com.pkest.netty.initializer.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by wuzhonggui on 2017/2/24.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class CtpServerBootstrap extends CtpBootstrap {
    protected int port;
    protected EventLoopGroup boss;
    protected EventLoopGroup worker;
    protected ServerBootstrap bootstrap;
    private static final Logger logger = LoggerFactory.getLogger(CtpServerBootstrap.class);

    public CtpServerBootstrap(int port, String configLocation){
        this.port = port;
        setConfigLocation(configLocation);
        setLastCtpAdapter(new LastCtpAdapter() {
            @Override
            public CtpProtocolHandler addLastHandler(CtpBootstrap bootstrap) {
                return new CtpProtocolHandler(bootstrap);
            }
        });
        init();
    }

    public void setChannelHandler(){
        bootstrap.childHandler(new ServerInitializer(this));
    }

    public void init(){
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);  //连接数
        bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);  //长连接
        setChannelHandler();
    }

    public void start() {
        if (closed){
            return;
        }
        try{
            ChannelFuture future = bootstrap.bind(port);
            future.addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if(f.isSuccess()){
                        channel = f.channel();
                        startSuccessed();
                        unsetAgainCount();
                    }else if(againCount > 0){
                        TimeUnit.SECONDS.sleep(againInterval);
                        logger.error("第 {} 次重启", againCount);
                        start();
                        againCount--;
                    }else{
                        startFailed(new RuntimeException("重启服务失败"));
                        stop();
                    }
                }
            });
        }catch(Exception e){
            startFailed(e);
            stop();
        }
    }

    public void stop(){
        if (null != worker) {
            worker.shutdownGracefully();
        }
        if (null != boss) {
            boss.shutdownGracefully();
        }
    }
}
