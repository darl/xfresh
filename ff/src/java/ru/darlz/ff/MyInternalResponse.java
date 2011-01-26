package ru.darlz.ff;

import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 26.01.11
 * Time: 6:16
 */
public class MyInternalResponse implements InternalResponse {
    public MyInternalResponse(HttpServletResponse httpServletResponse) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void redirectTo(String path) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void add(Object object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void putAttribute(String name, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Object> getData() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRedir() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getAttribute(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Writer getWriter() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addError(ErrorInfo errorInfo) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ErrorInfo> getErrors() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCookies(Map<String, String> cookies) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeCookie(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHeader(String name, String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHttpStatus(int statusCode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
