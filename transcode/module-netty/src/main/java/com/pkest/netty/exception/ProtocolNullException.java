package com.pkest.netty.exception;

/**
 * Created by wuzhonggui on 2018/2/1.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class ProtocolNullException extends Exception {

    public ProtocolNullException(String name) {
        super("Protocol [" + name + "] not found!");
    }
}
