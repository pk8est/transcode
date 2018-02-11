package com.pkest.transcode.common.protocol;

import com.pkest.netty2.protocol.ObjectProtocol;

/**
 * Created by wuzhonggui on 2017/2/7.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
//@NettyProtocol("Login2Protocol")
public class Login2Protocol extends ObjectProtocol {

    private String channelId;
    private String username;
    private String password;
    private String ip;

    public Login2Protocol(String channelId) {
        setChannelId(channelId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
