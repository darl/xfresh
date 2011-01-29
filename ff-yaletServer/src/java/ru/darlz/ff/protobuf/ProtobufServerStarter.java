package ru.darlz.ff.protobuf;

import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 29.01.11
 * Time: 3:21
 */
public class ProtobufServerStarter implements InitializingBean {
    private static final Logger log = Logger.getLogger(ProtobufServerStarter.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() {
        final long st = System.currentTimeMillis();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerRpcConnectionFactory serverConnectionFactory =
                SocketRpcConnectionFactories.createServerRpcConnectionFactory(_port, -1, null);

        RpcServer rpcServer = new RpcServer(serverConnectionFactory, threadPool, true);
        rpcServer.registerBlockingService(RemoteYaletProcessor.Processor.newReflectiveBlockingService(_service));

        rpcServer.startServer();

        log.info("Protobuf server started: " + (System.currentTimeMillis() - st) + " ms");
    }

    @Required
    public void setPort(int _port) {
        this._port = _port;
    }

    @Required
    public void setProtobufImpl(ProtobufYaletProcessorImpl _service) {
        this._service = _service;
    }

    int _port;
    ProtobufYaletProcessorImpl _service;
}
