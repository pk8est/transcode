package com.pkest.transcode.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2017/2/6.
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
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
