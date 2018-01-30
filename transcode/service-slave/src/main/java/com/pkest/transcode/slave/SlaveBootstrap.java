package com.pkest.transcode.slave;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.CtpClientBootstrap;
import com.pkest.netty.event.CallbackEvent;
import com.pkest.netty.handler.CtpProtocolHandler;
import com.pkest.netty.handler.LastCtpAdapter;
import com.pkest.transcode.slave.dispatch.DispatcherMasterCtpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/2/6.
 */
public class SlaveBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(SlaveBootstrap.class);

    public static void main(String[] args) {
        logger.info("main");
        CtpClientBootstrap client = new CtpClientBootstrap(9001, "127.0.0.1");
        client.setLastCtpAdapter(new LastCtpAdapter() {
            @Override
            public CtpProtocolHandler addLastHandler(CtpBootstrap bootstrap) {
                return new DispatcherMasterCtpHandler(bootstrap);
            }
        });
        client.setCallbackEvent(new CallbackEvent() {
            @Override
            public void success(CtpBootstrap bootstrap) {
                logger.info("connect server successed!");
            }

            @Override
            public void fail(Exception e, CtpBootstrap bootstrap) {
                logger.info("connect server fail!");
            }
        });
        client.start();
    }

}
