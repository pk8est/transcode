package com.pkest.netty.util;

import com.pkest.netty.annotation.Protocol;
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

    static {
        scanProtocolPacket();
    }

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
        new FastClasspathScanner(PropertiesUtil.getScanProtocolPacket())
                .matchClassesWithAnnotation(Protocol.class, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                protocols.put(aClass.getAnnotation(Protocol.class).value(), aClass);
            }
        }).scan();

    }

}
