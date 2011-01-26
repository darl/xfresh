package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 26.01.11
 * Time: 6:26
 */
public class FileUploadYalet implements Yalet {
    public void process(InternalRequest req, InternalResponse res) {

        try {
            res.add(req.getClass().getName());
            Proxy.getInvocationHandler(req).invoke(req, MyInternalRequest.class.getMethod("getAttrib", String.class), new String[]{"org.mortbay.servlet.MultiPartFilter.files"});
        } catch (Throwable throwable) {
            throwable.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //        req.invoke()
//        res.add(r.getAttrib("org.mortbay.servlet.MultiPartFilter.files"));
//        res.add(r.getAttrib("userfile"));
//        res.add(r.getR().getAttributeNames());
    }
}
