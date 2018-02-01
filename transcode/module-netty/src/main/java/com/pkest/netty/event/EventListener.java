package com.pkest.netty.event;

import com.pkest.netty.protocol.CtpProtocol;

/**
 * Created by wuzhonggui on 2018/2/1.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class EventListener <T extends CtpProtocol>{

    public abstract void handler(T message);

}
