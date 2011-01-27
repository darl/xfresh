package ru.darlz.ff.thrift;

import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 14:09
 */
public class ThriftServerStarter implements InitializingBean {
    private static final Logger log = Logger.getLogger(ThriftServerStarter.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() throws TTransportException {
        final long st = System.currentTimeMillis();

        final TNonblockingServerSocket socket = new TNonblockingServerSocket(_port);
        final RemoteYaletProcessor.Processor processor = new RemoteYaletProcessor.Processor(_handler);
        final TServer server = new THsHaServer(processor, socket,
                new TFramedTransport.Factory(), new TCompactProtocol.Factory());

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                server.serve();
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        log.info("Thrift server started: " + (System.currentTimeMillis() - st) + " ms");
    }

    @Required
    public void setPort(int _port) {
        this._port = _port;
    }

    int _port;

    public void setHandler(Handler _handler) {
        this._handler = _handler;
    }

    Handler _handler;
}
