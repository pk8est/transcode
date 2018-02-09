package com.pkest.netty.event;


import com.pkest.netty.CtpBootstrap;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface NettyCallbackEvent {

    void success(CtpBootstrap bootstrap);
    void fail(Exception e, CtpBootstrap bootstrap);

}
