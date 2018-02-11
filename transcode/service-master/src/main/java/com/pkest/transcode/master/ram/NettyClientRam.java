package com.pkest.transcode.master.ram;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzhonggui on 2018/2/11.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class NettyClientRam {

    protected static List<ChannelHandlerContext> list = new ArrayList<>();

    public static void add(ChannelHandlerContext ctx){
        list.add(ctx);
    }

    public static ChannelHandlerContext random(){
        int size = list.size();
        if(size == 0){
            return null;
        }
        int random = (int) (Math.random() * size);
        return list.get(random);
    }

}
