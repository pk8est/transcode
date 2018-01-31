package com.pkest.netty.handler;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.CtpClientBootstrap;
import com.pkest.netty.protocol.CtpProtocol;
import com.pkest.netty.util.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.LastHttpContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhonggui on 2017/2/25.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class CtpProtocolHandler extends WapperHandler<CtpProtocol>{

    protected String channelId = "";
    protected CtpBootstrap bootstrap;
    protected String remoteIp;
    protected int remotePort;
    protected String localIp;
    protected int localPort;
    protected Boolean setAddress = false;
    private static final Logger logger = LoggerFactory.getLogger(CtpProtocolHandler.class);

    public CtpProtocolHandler() {
        super();
    }

    public CtpProtocolHandler(CtpBootstrap bootstrap) {
        super();
        this.bootstrap = bootstrap;
        if(bootstrap != null && bootstrap instanceof CtpClientBootstrap){
            setChannelId(bootstrap.getChannelId());
        }else{
            setChannelId(getRandomID());
        }
    }

    public String getRandomID() {
        return StringUtil.getUUID32();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public CtpBootstrap getBootstrap() {
        return bootstrap;
    }

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        setAddress(ctx);
        active(ctx);
    }

    public void setAddress(ChannelHandlerContext ctx) throws Exception {
        if(!setAddress){
            setRemoteAddress(ctx.channel().remoteAddress());
            setLocalAddress(ctx.channel().localAddress());
            setAddress = true;
        }
    }

    public void active(ChannelHandlerContext ctx) throws Exception {}

    @Override
    public void dispatch(ChannelHandlerContext ctx, CtpProtocol obj) throws Exception {
        throw new Exception();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public final void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if(bootstrap != null && bootstrap instanceof CtpClientBootstrap){
            bootstrap.start();
        }
        inactive(ctx);
    }

    public void inactive(ChannelHandlerContext ctx) throws Exception {}

    public static void close(ChannelHandlerContext ctx){
        close(ctx.channel());
    }

    public static void close(Channel channel){
        try {
            channel.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            logger.warn("ctx close异常: {}" , e.getMessage());
        }
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public void setRemoteAddress(SocketAddress remoteAddress) {
        Map<String, String> map = splitAddress(remoteAddress);
        setRemoteIp(map.get("ip"));
        setRemotePort(Integer.valueOf(map.get("port")));
    }

    public void setLocalAddress(SocketAddress localAddress) {
        Map<String, String> map = splitAddress(localAddress);
        setLocalIp(map.get("ip"));
        setLocalPort(Integer.valueOf(map.get("port")));
    }

    public Map<String, String> splitAddress(SocketAddress localAddress){
        Map<String, String> map = new HashMap<>();
        map.put("ip", "");
        map.put("port", "");
        try {
            String[] arr = localAddress.toString().split("/");
            if(arr.length >= 2){
                String[] arr1 = arr[1].toString().split(":");
                if(arr1.length == 2){
                    map.put("ip", arr1[0]);
                    map.put("port", arr1[1]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public String getLocalIp() {
        return localIp;
    }

    public int getLocalPort() {
        return localPort;
    }
}
