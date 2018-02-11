package com.pkest.transcode.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2017/2/6.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = "com.pkest.transcode.master")
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
