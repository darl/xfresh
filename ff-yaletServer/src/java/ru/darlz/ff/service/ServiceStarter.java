package ru.darlz.ff.service;

import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 02.02.11
 * Time: 21:58
 */
public class ServiceStarter implements InitializingBean {
    private static final Logger log = Logger.getLogger(ServiceStarter.class);

    private int thriftPort;
    private int protobufPort;

    public void setThriftPort(int thriftPort) {
        this.thriftPort = thriftPort;
    }

    public void setProtobufPort(int protobufPort) {
        this.protobufPort = protobufPort;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() throws TTransportException {
        initThrift();
        initProtobuf();
    }

    private void initProtobuf() {
        final long st = System.currentTimeMillis();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerRpcConnectionFactory serverConnectionFactory =
                SocketRpcConnectionFactories.createServerRpcConnectionFactory(protobufPort, -1, null);

        RpcServer rpcServer = new RpcServer(serverConnectionFactory, threadPool, true);
        rpcServer.registerBlockingService(ProtobufService.Service.newReflectiveBlockingService(new ProtobufServiceImpl(_service)));

        rpcServer.startServer();

        log.info("Protobuf service server started: " + (System.currentTimeMillis() - st) + " ms");
    }

    private void initThrift() throws TTransportException {
        final long st = System.currentTimeMillis();

        final TNonblockingServerSocket socket = new TNonblockingServerSocket(thriftPort);
        final ThriftService.Processor processor = new ThriftService.Processor(new ThriftServiceImpl(_service));
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
        log.info("Thrift service server started: " + (System.currentTimeMillis() - st) + " ms");
    }


    private ServiceImpl _service;

    public void setServiceImpl(ServiceImpl _service) {
        this._service = _service;
    }
}
