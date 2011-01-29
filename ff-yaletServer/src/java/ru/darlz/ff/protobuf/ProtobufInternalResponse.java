package ru.darlz.ff.protobuf;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 29.01.11
 * Time: 5:33
 */
public class ProtobufInternalResponse implements InternalResponse {
    @Override
    public void redirectTo(String path) {
        _resBuilder.setRedirectTo(path);
    }

    @Override
    public void add(Object object) {
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

        _resBuilder.addData(ret);
    }

    @Override
    public void putAttribute(String name, Object value) {
        _resBuilder.addAttributes(RemoteYaletProcessor.MapEntry.newBuilder()
                .setKey(name)
                .setValue(value.toString())
                .build());
    }

    @Override
    public List<Object> getData() {
        List<Object> ret = new ArrayList<Object>();
        ret.addAll(_resBuilder.getDataList());
        return ret;
    }

    @Override
    public String getRedir() {
        return _resBuilder.getRedirectTo();
    }

    @Override
    public Object getAttribute(String name) {
        for (RemoteYaletProcessor.MapEntry e : _resBuilder.getAttributesList())
            if (e.getKey().equalsIgnoreCase(name))
                return e.getValue();

        return null;
    }

    @Override
    public Writer getWriter() throws IOException {
        return null;  //ignore
    }

    @Override
    public void addError(ErrorInfo errorInfo) {
        _resBuilder.addErrors(errorInfo.getMessageCode());
    }

    @Override
    public List<ErrorInfo> getErrors() {
        List<ErrorInfo> ret = new ArrayList<ErrorInfo>();
        for (String s : _resBuilder.getErrorsList())
            ret.add(new ErrorInfo(s));
        return ret;
    }

    @Override
    public void setCookies(Map<String, String> cookies) {
        for (Map.Entry<String, String> e : cookies.entrySet())
            _resBuilder.addCookies(RemoteYaletProcessor.MapEntry.newBuilder()
                    .setKey(e.getKey())
                    .setValue(e.getValue())
                    .build());
    }

    @Override
    public void removeCookie(String name) {
        _resBuilder.addCookies(RemoteYaletProcessor.MapEntry.newBuilder()
                .setKey(name)
                .setValue("__deleted__")
                .build());
    }

    @Override
    public void clear() {
        _resBuilder.clearData();
        _resBuilder.clearCookies();
    }

    @Override
    public void setHeader(String name, String value) {
        _resBuilder.addHeaders(RemoteYaletProcessor.MapEntry.newBuilder()
                .setKey(name)
                .setValue(value)
                .build());
    }

    @Override
    public void setHttpStatus(int statusCode) {
        _resBuilder.setHttpStatus(statusCode);
    }

    public ProtobufInternalResponse(RemoteYaletProcessor.RemoteInternalResponse.Builder _resBuilder) {
        this._resBuilder = _resBuilder;
    }

    public RemoteYaletProcessor.RemoteInternalResponse build() {
        return _resBuilder.build();
    }

    private RemoteYaletProcessor.RemoteInternalResponse.Builder _resBuilder;
}
