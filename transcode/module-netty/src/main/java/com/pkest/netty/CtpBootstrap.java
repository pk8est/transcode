package com.pkest.netty;

import com.pkest.netty.event.NettyCallbackEvent;
import com.pkest.netty.event.NettyEventHandler;
import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.handler.DispatcherCtpHandler;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.CommonUtil;
import com.pkest.netty.util.ScanAnnotationUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.lang.reflect.ParameterizedType;
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
    protected DispatcherCtpHandler dispatcherCtpHandler;
    protected NettyCallbackEvent callbackEvent;
    protected CtpProtocolHandler lastHandler;
    private static final Logger logger = LoggerFactory.getLogger(CtpBootstrap.class);
    private Map<Class, List<NettyEventHandler>> eventListeners = new HashMap<>();
    private XmlWebApplicationContext context;

    public abstract void setChannelHandler();

    public void setDispatcherCtpHandler(DispatcherCtpHandler dispatcherCtpHandler){
        this.dispatcherCtpHandler = dispatcherCtpHandler;
    }

    public DispatcherCtpHandler getDispatcherCtpHandler(){
        return dispatcherCtpHandler;
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

    public NettyCallbackEvent getCallbackEvent() {
        return callbackEvent;
    }

    public void setCallbackEvent(NettyCallbackEvent callbackEvent) {
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

    public void addEventListener(Class<CtpProtocol> clazz, NettyEventHandler eventHandler){
        ScanAnnotationUtil.addEventHandler(CommonUtil.getProtocolAnnotationOrClassName(clazz), eventHandler);
    }

    public void addEventListener(NettyEventHandler eventHandler){
        Class clazz = (Class <T>)((ParameterizedType) eventHandler.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        addEventListener(clazz, eventHandler);
    }

    public Map<Class, List<NettyEventHandler>> getEventListeners(){
        return eventListeners;
    }

    public List<NettyEventHandler> getEventListeners(Class<CtpProtocol> clazz){
        return eventListeners.get(clazz);
    }

    public ChannelFuture send(Channel channel, CtpProtocol obj) {
        return getLastHandler().send(channel, obj);
    }
}
