package ru.darlz.ff.protobuf;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.ServiceException;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.springframework.beans.factory.annotation.Required;
import ru.darlz.ff.RemoteYalet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 28.01.11
 * Time: 1:33
 */
public class RemoteProtobufYalet extends RemoteYalet {

    public RemoteProtobufYalet() {
    }

    private RemoteYaletProcessor.ParametersEntry paramsValue(String name, List<String> l, RemoteYaletProcessor.ParametersEntry.Builder b) {
        b.setKey(name);
        b.addAllValues(l);
        return b.build();
    }

    @Override
    public void process(InternalRequest req, InternalResponse res) {
        RemoteYaletProcessor.RemoteInternalResponse _res = null;
        RemoteYaletProcessor.RemoteInternalRequest _req;

        //init request
        List<RemoteYaletProcessor.MapEntry> cookieList = new LinkedList<RemoteYaletProcessor.MapEntry>();
        List<RemoteYaletProcessor.ParametersEntry> params = new LinkedList<RemoteYaletProcessor.ParametersEntry>();
        List<RemoteYaletProcessor.MapEntry> headers = new LinkedList<RemoteYaletProcessor.MapEntry>();

        RemoteYaletProcessor.MapEntry.Builder mapEntryBuilder = RemoteYaletProcessor.MapEntry.newBuilder();
        RemoteYaletProcessor.ParametersEntry.Builder paramEntryBuilder = RemoteYaletProcessor.ParametersEntry.newBuilder();

        for (Map.Entry<String, String> e : req.getCookies().entrySet()) {
            mapEntryBuilder.setKey(e.getKey());
            mapEntryBuilder.setValue(e.getValue());
            cookieList.add(mapEntryBuilder.build());
        }

        for (Map.Entry<String, List<String>> e : req.getAllParameters().entrySet()) {
            params.add(paramsValue(e.getKey(), e.getValue(), paramEntryBuilder));
        }

        headers.add(mapEntryBuilder.setKey("Referer").setValue(req.getHeader("Referer")).build());
        headers.add(mapEntryBuilder.setKey("User-Agent").setValue(req.getHeader("User-Agent")).build());

        _req = RemoteYaletProcessor.RemoteInternalRequest.newBuilder()
                .setRealPath(req.getRealPath())
                .setNeedTransform(req.needTransform())
                .addAllCookies(cookieList)
                .addAllParameters(params)
                .setRequestUrl(req.getRequestURL())
                .setQueryString(req.getQueryString())
                .setRemoteAddr(req.getRemoteAddr())
                .addAllHeaders(headers)
                .setUserId(req.getUserId() != null ? req.getUserId() : 0)
                .setYaletName(_yaletName)            //most important
                .build();


        //init connection
        RpcConnectionFactory clientConnectionFactory =
                SocketRpcConnectionFactories.createRpcConnectionFactory(_host, _port);
        BlockingRpcChannel channel = RpcChannels.newBlockingRpcChannel(clientConnectionFactory);
        SocketRpcController controller = new SocketRpcController();
        RemoteYaletProcessor.Processor.BlockingInterface blockingProc =
                RemoteYaletProcessor.Processor.newBlockingStub(channel);

        //process
        try {
            _res = blockingProc.process(controller, _req);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        assert _res != null;

        if (_res.hasRedirectTo() && !_res.getRedirectTo().equals(""))
            res.redirectTo(_res.getRedirectTo());
        if (_res.getCookiesCount() > 0) {
            Map<String, String> cookies = new HashMap<String, String>();
            for (RemoteYaletProcessor.MapEntry e : _res.getCookiesList()) {
                if (e.getValue().equalsIgnoreCase("__deleted__"))
                    res.removeCookie(e.getKey());
                else
                    cookies.put(e.getKey(), e.getValue());
            }
            res.setCookies(cookies);
        }
        if (_res.hasHttpStatus() && _res.getHttpStatus() != 0)
            res.setHttpStatus(_res.getHttpStatus());
        if (_res.getDataCount() > 0)
            for (String data : _res.getDataList())
                res.add(data);
        if (_res.getErrorsCount() > 0)
            for (String err : _res.getDataList())
                res.addError(new ErrorInfo(err));
        if (_res.getAttributesCount() > 0)
            for (RemoteYaletProcessor.MapEntry e : _res.getAttributesList())
                res.putAttribute(e.getKey(), e.getValue());
    }


    private String _host;
    private int _port;

    @Required
    public void setPort(int _port) {
        this._port = _port;
    }

    @Required
    public void setHost(String _host) {
        this._host = _host;
    }
}
