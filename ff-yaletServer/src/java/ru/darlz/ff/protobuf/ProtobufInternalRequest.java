package ru.darlz.ff.protobuf;

import net.sf.xfresh.core.InternalRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 29.01.11
 * Time: 5:28
 */
public class ProtobufInternalRequest implements InternalRequest {
    public ProtobufInternalRequest(RemoteYaletProcessor.RemoteInternalRequest req) {
        _req = req;
    }

    @Override
    public String getRealPath() {
        return _req.getRealPath();
    }

    @Override
    public boolean needTransform() {
        return _req.getNeedTransform();
    }

    @Override
    public String getParameter(String name) {
        if (_params == null) getAllParameters();
        List<String> it = _params.get(name);

        if (it == null) return null;
        if (it.isEmpty())
            return null;
        return it.get(0);
    }

    @Override
    public String[] getParameters(String name) {
        if (_params == null) getAllParameters();
        List<String> it = _params.get(name);
        if (it == null) return null;
        if (it.isEmpty()) return null;

        String[] ret = new String[it.size()];
        for (int i = 0; i < it.size(); i++)
            ret[i] = it.get(i);
        return ret;
    }

    @Override
    public Map<String, String> getCookies() {
        _cookies = new HashMap<String, String>();
        for (RemoteYaletProcessor.MapEntry e : _req.getCookiesList())
            _cookies.put(e.getKey(), e.getValue());

        return _cookies;
    }

    @Override
    public Map<String, List<String>> getAllParameters() {
        _params = new HashMap<String, List<String>>();
        for (RemoteYaletProcessor.ParametersEntry e : _req.getParametersList()) {
            _params.put(e.getKey(), e.getValuesList());
        }
        return _params;
    }

    @Override
    public String getRequestURL() {
        return _req.getRequestUrl();
    }

    @Override
    public String getQueryString() {
        return _req.getQueryString();
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
        return _req.getRemoteAddr();
    }

    @Override
    public String getHeader(String name) {
        for (RemoteYaletProcessor.MapEntry e : _req.getHeadersList())
            if (e.getKey().equals(name))
                return e.getValue();

        return null;
    }

    @Override
    public String getCookie(String name) {
        if (_cookies == null) getCookies();
        return _cookies.get(name);
    }

    @Override
    public Long getUserId() {
        return _req.getUserId();
    }

    private RemoteYaletProcessor.RemoteInternalRequest _req;
    private Map<String, List<String>> _params = null;
    private Map<String, String> _cookies = null;
}
