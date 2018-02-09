/**
 * 
 */
package com.pkest.netty2.util;

import com.pkest.netty2.annotation.NettyProtocol;

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
