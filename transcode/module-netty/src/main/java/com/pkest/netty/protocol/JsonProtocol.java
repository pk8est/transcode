package com.pkest.netty.protocol;

import com.pkest.netty.proto.WrapperOuterClass;
import com.pkest.netty.util.GsonUtil;

/**
 * Created by wuzhonggui on 2017/2/16.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class JsonProtocol extends CtpProtocol {

    public JsonProtocol(Class clazz) {
        super(clazz, WrapperOuterClass.Wrapper.ContectType.JSON);
    }


    public byte[] getContent(){
        return GsonUtil.getGson().toJson(this).getBytes();
    }


    public void setContent(byte[] buf){

    }
}
