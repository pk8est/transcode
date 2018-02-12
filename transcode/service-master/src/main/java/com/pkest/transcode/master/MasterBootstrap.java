package com.pkest.transcode.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Administrator on 2017/2/6.
 */
@SpringBootApplication
public class MasterBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(MasterBootstrap.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MasterBootstrap.class, args);
        NettyServer nettyServer = context.getBean(NettyServer.class);
        nettyServer.start();

       /*NettyServer nettyServer = new NettyServer();
        nettyServer.start();*/
    }

}
