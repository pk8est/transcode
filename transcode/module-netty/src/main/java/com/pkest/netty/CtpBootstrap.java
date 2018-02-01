package com.pkest.netty;

import com.pkest.netty.event.CallbackEvent;
import com.pkest.netty.event.EventListener;
import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.handler.LastCtpAdapter;
import com.pkest.netty.protocol.CtpProtocol;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzhonggui on 2017/2/24.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public abstract class CtpBootstrap <T extends CtpProtocol> {

    protected int port;
    protected String host;
    protected EventLoopGroup group;
    protected Channel channel;
    protected String channelId;
    protected Boolean closed = false;
    protected int againCount = 3;
    protected int _againCount = 3;
    protected int againInterval = 3;
    protected String configLocation;
    protected LastCtpAdapter lastCtpAdapter;
    protected CallbackEvent callbackEvent;
    protected CtpProtocolHandler lastHandler;
    private static final Logger logger = LoggerFactory.getLogger(CtpBootstrap.class);
    private Map<Class, List<EventListener>> eventListeners = new HashMap<>();
    private XmlWebApplicationContext context;

    public abstract void setChannelHandler();

    public void setLastCtpAdapter(LastCtpAdapter lastCtpAdapter){
        this.lastCtpAdapter = lastCtpAdapter;
    }

    public LastCtpAdapter getLastCtpAdapter(){
        return lastCtpAdapter;
    }

    public void setLastHandler(CtpProtocolHandler handler) {
        this.lastHandler = handler;
    }

    public CtpProtocolHandler getLastHandler(){
        return this.lastHandler;
    }

    public abstract void init();

    public abstract void start();

    public abstract void stop();

    public void setAgainCount(int againCount) {
        this.againCount = _againCount= againCount;
    }

    public void unsetAgainCount() {
        setAgainCount(_againCount);
    }

    public void setAgainInterval(int againInterval) {
        this.againInterval = againInterval;
    }

    public String getConfigLocation(){
        return configLocation;
    }

    public void setConfigLocation(String configLocation){
        this.configLocation = configLocation;
    }

    public CallbackEvent getCallbackEvent() {
        return callbackEvent;
    }

    public void setCallbackEvent(CallbackEvent callbackEvent) {
        this.callbackEvent = callbackEvent;
    }

    public Channel getChannel() {
        return channel;
    }

    protected void startSuccessed(){
        successed();
        if(getCallbackEvent() != null){
            getCallbackEvent().success(this);
        }
    }

    protected void startFailed(Exception e){
        failed(e);
        if(getCallbackEvent() != null){
            getCallbackEvent().fail(e, this);
        }
    }

    public void successed(){

    }

    public void failed(Exception e){

    }

    public void setContext(XmlWebApplicationContext context) {
        this.context = context;
    }

    public XmlWebApplicationContext getContext() {
        return context;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void addEventListener(Class<CtpProtocol> clazz, EventListener eventListener){
        if(eventListeners.containsKey(clazz)){
            eventListeners.get(clazz).add(eventListener);
        }else{
            List<EventListener> events = new ArrayList<>();
            events.add(eventListener);
            eventListeners.put(clazz, events);
        }
    }

    public void addEventListener(EventListener eventListener){
        Class clazz = (Class <T>)((ParameterizedType)eventListener.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        addEventListener(clazz, eventListener);
    }

    public Map<Class, List<EventListener>> getEventListeners(){
        return eventListeners;
    }

    public List<EventListener> getEventListeners(Class<CtpProtocol> clazz){
        return eventListeners.get(clazz);
    }
}
