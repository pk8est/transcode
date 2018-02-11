package com.pkest.netty.util;

import com.pkest.netty.annotation.NettyProtocol;
import io.netty.channel.ChannelHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhonggui on 2018/2/1.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */


public class ScanAnnotationUtil {

    protected static Map<String, Class> protocols = new HashMap();

    public static Map<String, Class> getProtocols() {
        return protocols;
    }

    public static Class getProtocol(String key) {
        if(protocols.containsKey(key)){
            return protocols.get(key);
        }
        return null;
    }

    public static void scanProtocolClass(Class<? extends ChannelHandler> clazz){
        for(Type type: getGenericSuperclass(clazz)){
            Class aClass = (Class) type;
            if(aClass.isAnnotationPresent(NettyProtocol.class)){
                protocols.put(((NettyProtocol)aClass.getAnnotation(NettyProtocol.class)).value(), aClass);
            }
        }
    }

    public static Type[] getGenericSuperclass(Class<? extends ChannelHandler> clazz){
        Type type = clazz.getGenericSuperclass();
        if(type instanceof ParameterizedType){
            return ((ParameterizedType) type).getActualTypeArguments();
        }
        return new Type[0];
    }

}
