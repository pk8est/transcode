package com.pkest.netty2.protocol;

import com.pkest.netty2.util.GsonUtil;
import com.pkest.netty2.proto.WrapperOuterClass;

/**
 * Created by wuzhonggui on 2017/2/16.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class ObjectProtocol extends CtpProtocol {

    public ObjectProtocol() {
        super(WrapperOuterClass.Wrapper.ContectType.OBJECT);
    }


    public byte[] getContent(){
        return GsonUtil.getGson().toJson(this).getBytes();
    }


    public void setContent(byte[] buf){

    }
}
