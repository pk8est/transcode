package com.pkest.netty.util;

import com.pkest.netty.annotation.NettyProtocolHandler;
import com.pkest.netty.annotation.NettyProtocol;
import com.pkest.netty.event.NettyEventHandler;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzhonggui on 2018/2/1.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */


public class ScanAnnotationUtil {

    protected static Map<String, Class> protocols = new HashMap();
    protected static Map<String, List<NettyEventHandler>> eventHandlers = new HashMap();
    protected static Map<String, List<Class>> eventHandlerClasss = new HashMap();

    static {
        scanProtocolPacket();
        scanEventHandlerBind();
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

    public static Map<String, List<NettyEventHandler>> getEventHandlers() {
        return eventHandlers;
    }

    public static List<NettyEventHandler> getEventHandler(String key) {
        if(eventHandlers.containsKey(key)){
            return eventHandlers.get(key);
        }
        return new ArrayList<NettyEventHandler>();
    }

    public static void addEventHandler(String key, NettyEventHandler handler){
        if(eventHandlers.containsKey(key)){
            eventHandlers.get(key).add(handler);
        }else{
            List<NettyEventHandler> events = new ArrayList();
            events.add(handler);
            eventHandlers.put(key, events);
        }
    }

    public static Map<String, List<Class>> getEventHandlerClasss() {
        return eventHandlerClasss;
    }

    public static List<Class> getEventHandlerClass(String key) {
        if(eventHandlerClasss.containsKey(key)){
            return eventHandlerClasss.get(key);
        }
        return new ArrayList();
    }

    public static void addEventHandlerClass(String key, Class clazz){
        if(eventHandlerClasss.containsKey(key)){
            eventHandlerClasss.get(key).add(clazz);
        }else{
            List<Class> events = new ArrayList();
            events.add(clazz);
            eventHandlerClasss.put(key, events);
        }
    }

    public static void scanProtocolPacket(){
        //new FastClasspathScanner(PropertiesUtil.getScanProtocolPacket())
        new FastClasspathScanner()
            .matchClassesWithAnnotation(NettyProtocol.class, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                protocols.put(aClass.getAnnotation(NettyProtocol.class).value(), aClass);
            }
        }).scan();
    }

    public static void scanEventHandlerBind(){
        new FastClasspathScanner()
            .matchClassesWithAnnotation(NettyProtocolHandler.class, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                addEventHandlerClass(aClass.getAnnotation(NettyProtocolHandler.class).value(), aClass);
            }
        }).scan();
    }

}
