package ru.darlz.ff;

import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 31.01.11
 * Time: 10:12
 */
public class FakeInternalResponse implements InternalResponse {
    @Override
    public void redirectTo(String path) {

    }

    @Override
    public void add(Object object) {

    }

    @Override
    public void putAttribute(String name, Object value) {

    }

    @Override
    public List<Object> getData() {
        return null;
    }

    @Override
    public String getRedir() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public Writer getWriter() throws IOException {
        return null;
    }

    @Override
    public void addError(ErrorInfo errorInfo) {

    }

    @Override
    public List<ErrorInfo> getErrors() {
        return null;
    }

    @Override
    public void setCookies(Map<String, String> cookies) {

    }

    @Override
    public void removeCookie(String name) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void setHeader(String name, String value) {

    }

    @Override
    public void setHttpStatus(int statusCode) {

    }
}
