/**
 * 
 */
package com.pkest.netty.util;

import com.pkest.netty.annotation.NettyProtocol;

import java.lang.annotation.Annotation;

public class CommonUtil {

    public static String getProtocolAnnotationOrClassName(Class<?> clazz){
        Annotation annotation = clazz.getAnnotation(NettyProtocol.class);
        if(annotation == null){
            return clazz.getName();
        }
        return ((NettyProtocol)annotation).value();
    }

}
