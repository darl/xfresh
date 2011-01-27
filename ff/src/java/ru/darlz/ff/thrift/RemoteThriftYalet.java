package ru.darlz.ff.thrift;

import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Required;
import ru.darlz.ff.RemoteYalet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 1:02
 */
public class RemoteThriftYalet extends RemoteYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        //Setup the transport and protocol
        final TSocket socket = new TSocket(_host, _port, 300);
        final TTransport transport = new TFramedTransport(socket);
        final TProtocol protocol = new TCompactProtocol(transport);
        final RemoteYaletProcessor.Client client = new RemoteYaletProcessor.Client(protocol);

        //The transport must be opened before you can begin using
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<String, String>();
        if (req.getHeader("Referer") != null)
            headers.put("Referer", req.getHeader("Referer"));
        if (req.getHeader("User-Agent") != null)
            headers.put("User-Agent", req.getHeader("User-Agent"));

        String realPath = req.getRealPath();
        boolean needTransform = req.needTransform();
        Map<String, String> cookies = req.getCookies();
        Map<String, List<String>> params = req.getAllParameters();
        String requestUrl = req.getRequestURL();
        String queryString = req.getQueryString();
        String remoteAddr = req.getRemoteAddr();
        Long userId = req.getUserId();

        RemoteInternalRequest rReq = new RemoteInternalRequest(
                realPath,
                needTransform,
                cookies,
                params,
                requestUrl,
                queryString,
                remoteAddr,
                headers,
                userId == null ? 0 : userId);


        RemoteInternalResponse rRes = new RemoteInternalResponse(
                res.getRedir(),
                null, //cookies
                0,    //status
                null, //data
                null, //errors
                null,  //attributes
                null  //headers
        );

        //All hooked up, start using the service
        try {
            rRes = client.process(_yaletName, rReq, rRes);
        } catch (TException e) {
            e.printStackTrace();
        }


        if (rRes.isSetRedirectTo() && !rRes.redirectTo.equals(""))
            res.redirectTo(rRes.redirectTo);
        if (rRes.isSetCookies()) {
            res.setCookies(rRes.cookies);
            for (final Map.Entry<String, String> cookie : rRes.cookies.entrySet())
                if (cookie.getValue().equalsIgnoreCase("__deleted__"))
                    res.removeCookie(cookie.getKey());
        }
        if (rRes.isSetHttpStatus() && rRes.httpStatus != 0)
            res.setHttpStatus(rRes.httpStatus);
        if (rRes.isSetData())
            for (String data : rRes.data)
                res.add(data);
        if (rRes.isSetErrors())
            for (String err : rRes.errors)
                res.addError(new ErrorInfo(err));
        if (rRes.isSetAttributes())
            for (Map.Entry<String, String> attrib : rRes.attributes.entrySet())
                res.putAttribute(attrib.getKey(), attrib.getValue());

        transport.close();
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
