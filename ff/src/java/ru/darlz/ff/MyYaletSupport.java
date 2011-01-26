package ru.darlz.ff;

import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.ext.ExtYaletSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 26.01.11
 * Time: 6:32
 */
public class MyYaletSupport extends ExtYaletSupport {
    @Override
    public MyInternalRequest createRequest(HttpServletRequest httpServletRequest, String realPath) {
        return new MyInternalRequest(httpServletRequest, realPath);
    }

    @Override
    public InternalResponse createResponse(HttpServletResponse httpServletResponse) {
        return super.createResponse(httpServletResponse);
        //return new MyInternalResponse(httpServletResponse);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
