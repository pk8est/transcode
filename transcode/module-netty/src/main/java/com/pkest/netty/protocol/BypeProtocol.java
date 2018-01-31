package com.pkest.netty.protocol;


import com.pkest.netty.proto.WrapperOuterClass;

/**
 * Created by wuzhonggui on 2017/2/16.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class BypeProtocol extends CtpProtocol {

    public BypeProtocol() {
        super(WrapperOuterClass.Wrapper.ContectType.BYTE);
    }
    public byte[] bytes;
    public void setBytes(byte[] bytes){
        this.bytes = bytes;
    }

    public byte[] getContent(){
        return bytes;
    }

    public void setContent(byte[] bytes){
        setBytes(bytes);
    }
}
