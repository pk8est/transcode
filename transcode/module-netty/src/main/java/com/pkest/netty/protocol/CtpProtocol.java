package com.pkest.netty.protocol;

import com.google.protobuf.ByteString;
import com.pkest.netty.proto.WrapperOuterClass;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by wuzhonggui on 2017/2/7.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class CtpProtocol {

    transient private WrapperOuterClass.Wrapper.ContectType contectType;
    transient private String classType;
    transient protected byte[] content;

    public CtpProtocol(Class clazz, WrapperOuterClass.Wrapper.ContectType contectType) {
        setClassType(clazz.getName());
        setContentType(contectType);
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public WrapperOuterClass.Wrapper.ContectType getContentType() {
        return contectType;
    }

    public void setContentType(WrapperOuterClass.Wrapper.ContectType contentType) {
        this.contectType = contentType;
    }

    public abstract byte[] getContent();

    public abstract void setContent(byte[] buf) throws Exception;

    public WrapperOuterClass.Wrapper toWrapper(){
        WrapperOuterClass.Wrapper.Builder builder = builder();
        builder.setContent(ByteString.copyFrom(this.getContent()));
        return builder.build();
    }

    public WrapperOuterClass.Wrapper.Builder builder(){
        WrapperOuterClass.Wrapper.Builder builder = WrapperOuterClass.Wrapper.newBuilder();
        builder.setClassType(getClassType());
        builder.setContentType(getContentType());
        return builder;
    }

    public void release() {
        ReferenceCountUtil.release(this);
    }
}
