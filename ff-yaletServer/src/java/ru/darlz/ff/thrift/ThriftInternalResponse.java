package ru.darlz.ff.thrift;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import net.sf.xfresh.core.DefaultSaxGenerator;
import net.sf.xfresh.core.ErrorInfo;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.SaxGenerator;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 15:35
 */
public class ThriftInternalResponse implements InternalResponse {
    public ThriftInternalResponse(RemoteInternalResponse res) {
        _res = res;
    }

    @Override
    public void redirectTo(String path) {
        _res.setRedirectTo(path);
    }

    @Override
    public void add(Object object) {
        if (!_res.isSetData())
            _res.data = new ArrayList<String>();

        final OutputFormat DEFAULT_FORMAT = new OutputFormat("XML", "utf-8", false);
        XMLSerializer serializer;
        StringWriter stringWriter = new StringWriter();
        serializer = new XMLSerializer(stringWriter, DEFAULT_FORMAT);
        SaxGenerator generator = new DefaultSaxGenerator();
        try {
            generator.writeXml(serializer.asContentHandler(), Arrays.asList(object));
            stringWriter.close();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ret = StringUtils.removeStart(
                stringWriter.toString(),
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");

        _res.data.add(ret);
    }

    @Override
    public void putAttribute(String name, Object value) {
        if (!_res.isSetAttributes())
            _res.attributes = new HashMap<String, String>();
        _res.attributes.put(name, value.toString());
    }

    @Override
    public List<Object> getData() {
        if (!_res.isSetData()) return null;
        List<Object> ret = new ArrayList<Object>();
        ret.addAll(_res.data);
        return ret;
    }

    @Override
    public String getRedir() {
        return _res.redirectTo;
    }

    @Override
    public Object getAttribute(String name) {
        if (!_res.isSetAttributes()) return null;
        return _res.attributes.get(name);
    }

    @Override
    public Writer getWriter() throws IOException {
        return null;
    }

    @Override
    public void addError(ErrorInfo errorInfo) {
        if (!_res.isSetErrors())
            _res.errors = new ArrayList<String>();
        _res.errors.add(errorInfo.getMessageCode());
    }

    @Override
    public List<ErrorInfo> getErrors() {
        if (!_res.isSetErrors()) return null;
        List<ErrorInfo> ret = new ArrayList<ErrorInfo>();
        for (String err : _res.errors)
            ret.add(new ErrorInfo(err));
        return ret;
    }

    @Override
    public void setCookies(Map<String, String> cookies) {
        if (!_res.isSetCookies())
            _res.cookies = new HashMap<String, String>();
        for (Map.Entry<String, String> c : cookies.entrySet())
            _res.cookies.put(c.getKey(), c.getValue());
    }

    @Override
    public void removeCookie(String name) {
        if (!_res.isSetCookies())
            _res.cookies = new HashMap<String, String>();
        _res.cookies.put(name, "__deleted__");
    }

    @Override
    public void clear() {
        if (!_res.isSetData())
            _res.data.clear();
        if (_res.isSetCookies())
            _res.cookies.clear();
    }

    @Override
    public void setHeader(String name, String value) {
        if (_res.isSetHeaders())
            _res.headers = new HashMap<String, String>();
        _res.headers.put(name, value);
    }

    @Override
    public void setHttpStatus(int statusCode) {
        _res.setHttpStatus(statusCode);
    }

    private RemoteInternalResponse _res;
}
