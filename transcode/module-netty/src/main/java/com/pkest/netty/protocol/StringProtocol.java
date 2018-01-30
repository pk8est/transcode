package com.pkest.netty.protocol;

import com.pkest.netty.proto.WrapperOuterClass;
import com.pkest.netty.util.StringUtil;

/**
 * Created by wuzhonggui on 2017/2/16.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class StringProtocol extends CtpProtocol {
    protected String message;
    public StringProtocol(Class clazz) {
        super(clazz, WrapperOuterClass.Wrapper.ContectType.STRING);
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    public byte[] getContent(){
        if(StringUtil.isEmpty(message)){
            return "".getBytes();
        }
        return message.getBytes();
    }

    public void setContent(byte[] buf){
        message = new String(buf);
    }
}
