package com.pkest.netty.util;

import com.pkest.netty.annotation.NettyProtocol;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;

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

    public static void scanProtocolPacket(){
        //new FastClasspathScanner(PropertiesUtil.getScanProtocolPacket())
        new FastClasspathScanner()
            //.verbose()
            .matchClassesWithAnnotation(NettyProtocol.class, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                try {
                    //这里这样写是为了解决在sprint boot的情况下获取不到注解的bug
                    Class clazz = Class.forName(aClass.getName());
                    protocols.put(((NettyProtocol)clazz.getAnnotation(NettyProtocol.class)).value(), clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).scan();
    }

}
