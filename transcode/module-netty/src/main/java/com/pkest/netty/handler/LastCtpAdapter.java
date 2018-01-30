package com.pkest.netty.handler;

import com.pkest.netty.CtpBootstrap;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public interface LastCtpAdapter extends LastHandlerAdapter{

    CtpProtocolHandler addLastHandler(CtpBootstrap bootstrap);

}
