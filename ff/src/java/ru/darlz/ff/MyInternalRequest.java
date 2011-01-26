package ru.darlz.ff;

import net.sf.xfresh.core.SimpleInternalRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 26.01.11
 * Time: 6:14
 */
public class MyInternalRequest extends SimpleInternalRequest {
    HttpServletRequest hReq;

    protected MyInternalRequest(final SimpleInternalRequest src) {
        super(src);
    }

    protected MyInternalRequest(final HttpServletRequest httpRequest, final String realPath) {
        super(httpRequest, realPath);
        hReq = httpRequest;
    }

    public String getAttrib(String name) {
        return String.valueOf(hReq.getAttribute(name));
    }

    public HttpServletRequest getR() {
        return hReq;
    }
}
