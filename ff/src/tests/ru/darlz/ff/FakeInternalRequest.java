package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 31.01.11
 * Time: 10:12
 */
public class FakeInternalRequest implements InternalRequest {
    @Override
    public String getRealPath() {
        return "./web";
    }

    @Override
    public boolean needTransform() {
        return false;
    }

    @Override
    public String getParameter(String name) {
        return "";
    }

    @Override
    public String[] getParameters(String name) {
        return new String[1];
    }

    @Override
    public Map<String, String> getCookies() {
        return new HashMap<String, String>();
    }

    @Override
    public Map<String, List<String>> getAllParameters() {
        return new HashMap<String, List<String>>();
    }

    @Override
    public String getRequestURL() {
        return "/";
    }

    @Override
    public String getQueryString() {
        return "";
    }

    @Override
    public int getIntParameter(String name) {
        return 0;
    }

    @Override
    public int getIntParameter(String name, int defaultValue) {
        return 0;
    }

    @Override
    public String getRemoteAddr() {
        return "127.0.0.1";
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String getCookie(String name) {
        return "";
    }

    @Override
    public Long getUserId() {
        return 0l;
    }
}
