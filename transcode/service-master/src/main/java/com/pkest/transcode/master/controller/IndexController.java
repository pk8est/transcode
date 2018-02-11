package com.pkest.transcode.master.controller;

import com.pkest.transcode.common.protocol.LoginProtocol;
import com.pkest.transcode.master.ram.NettyClientRam;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wuzhonggui on 2018/2/11.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@RestController
public class IndexController {

    //@Value("${test.name}")
    private String name;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello/{myName}")
    String index(@PathVariable String myName) {
        String data = "Hello "+myName+"!!!";
        ChannelHandlerContext ctx = NettyClientRam.random();
        if(ctx != null){
            ctx.writeAndFlush(new LoginProtocol(data).toWrapper());
        }
        System.err.println(name);
        return data;
    }

}
