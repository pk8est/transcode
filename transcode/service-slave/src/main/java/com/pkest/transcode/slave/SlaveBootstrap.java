package com.pkest.transcode.slave;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Created by Administrator on 2017/2/6.
 */
@SpringBootApplication
public class SlaveBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(SlaveBootstrap.class);

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(SlaveBootstrap.class, args);
        NettyClient nettyClient = context.getBean(NettyClient.class);
        nettyClient.start();

        /*NettyClient nettyClient = new NettyClient();
        nettyClient.start();*/

    }

}
