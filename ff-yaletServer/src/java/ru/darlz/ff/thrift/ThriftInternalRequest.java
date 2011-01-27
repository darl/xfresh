package ru.darlz.ff.thrift;

import net.sf.xfresh.core.InternalRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 15:31
 */
public class ThriftInternalRequest implements InternalRequest {
    public ThriftInternalRequest(RemoteInternalRequest req) {
        _req = req;
    }

    @Override
    public String getRealPath() {
        return _req.getRealPath();
    }

    @Override
    public boolean needTransform() {
        return _req.needTransform;
    }

    @Override
    public String getParameter(String name) {
        if (!_req.isSetParameters())
            return null;
        List<String> values = _req.parameters.get(name);
        if (values == null || values.isEmpty())
            return null;
        return _req.parameters.get(name).get(0);
    }

    @Override
    public String[] getParameters(String name) {
        if (!_req.isSetParameters())
            return null;
        List<String> values = _req.parameters.get(name);
        String[] ret = new String[values.size()];
        for (int i = 0; i < values.size(); ++i)
            ret[i] = values.get(i);
        return ret;
    }

    @Override
    public Map<String, String> getCookies() {
        return _req.cookies;
    }

    @Override
    public Map<String, List<String>> getAllParameters() {
        return _req.parameters;
    }

    @Override
    public String getRequestURL() {
        return _req.requestUrl;
    }

    @Override
    public String getQueryString() {
        return _req.queryString;
    }

    @Override
    public int getIntParameter(String name) {
        return Integer.parseInt(getParameter(name));
    }

    @Override
    public int getIntParameter(String name, int defaultValue) {
        try {
            return Integer.parseInt(getParameter(name));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    public String getRemoteAddr() {
        return _req.remoteAddr;
    }

    @Override
    public String getHeader(String name) {
        if (!_req.isSetHeaders())
            return null;
        return _req.headers.get(name);
    }

    @Override
    public String getCookie(String name) {
        if (!_req.isSetCookies())
            return null;
        return _req.cookies.get(name);
    }

    @Override
    public Long getUserId() {
        return _req.userId;
    }

    private RemoteInternalRequest _req;
}
