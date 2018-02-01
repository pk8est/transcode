package com.pkest.transcode.slave;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.CtpClientBootstrap;
import com.pkest.netty.event.CallbackEvent;
import com.pkest.netty.event.EventListener;
import com.pkest.transcode.common.protocol.LoginProtocol;
import com.pkest.transcode.slave.dispatch.DispatcherMasterCtpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2017/2/6.
 */
public class SlaveBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(SlaveBootstrap.class);

    public static void main(String[] args) throws IOException {
        final CtpClientBootstrap client = new CtpClientBootstrap(9001, "127.0.0.1");
        final DispatcherMasterCtpHandler handler = new DispatcherMasterCtpHandler(client);
        client.setLastHandler(handler);
        handler.setChannelId("test");
        client.setCallbackEvent(new CallbackEvent() {
            @Override
            public void success(CtpBootstrap bootstrap) {
                logger.info("connect server successed!");
                //handler.send(client.getChannel(), new LoginProtocol("handsome wu"));
                client.send(new LoginProtocol("handsome wu"));
            }

            @Override
            public void fail(Exception e, CtpBootstrap bootstrap) {
                logger.info("connect server fail!");
            }
        });
        client.addEventListener(LoginProtocol.class, new EventListener<LoginProtocol>() {
            @Override
            public void handler(LoginProtocol message) {
                System.err.println("1. " + message.getChannelId());
            }
        });
        client.addEventListener(new EventListener<LoginProtocol>() {
            @Override
            public void handler(LoginProtocol message) {
                System.err.println("2. " + message.getChannelId());
            }
        });
        client.start();
    }

}
