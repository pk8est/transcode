package com.pkest.transcode.master;

import com.pkest.netty.CtpBootstrap;
import com.pkest.netty.CtpServerBootstrap;
import com.pkest.netty.event.CallbackEvent;
import com.pkest.transcode.master.dispatch.DispatcherMasterCtpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/2/6.
 */
public class MasterBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(MasterBootstrap.class);

    public static void main(String[] args) {
        CtpServerBootstrap server = new CtpServerBootstrap(9001, "127.0.0.1");
        server.setLastHandler(new DispatcherMasterCtpHandler(server));
        /*server.setLastCtpAdapter(new LastCtpAdapter() {
            @Override
            public CtpProtocolHandler addLastHandler(CtpBootstrap bootstrap) {
                return new DispatcherMasterCtpHandler(bootstrap);
            }
        });*/

        server.setCallbackEvent(new CallbackEvent() {
            @Override
            public void success(CtpBootstrap bootstrap) {
                logger.info("connect server successed!");
            }

            @Override
            public void fail(Exception e, CtpBootstrap bootstrap) {
                logger.info("connect server fail!");
            }
        });

        server.start();
    }

}
