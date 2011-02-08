package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.ext.ExtYaletSupport;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.XMLFilter;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 21:14
 */
public class RemoteYaletSupport extends ExtYaletSupport {

    private String serviceHost;
    private int thriftPort;
    private int protobufPort;

    @Override
    public XMLFilter createFilter(final InternalRequest request, final InternalResponse response) {
        return new RemoteYaletFilter(singleYaletProcessor, authHandler, request, response, resourceBase, saxGenerator,
                serviceHost, thriftPort, protobufPort);
    }

    @Required
    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    @Required
    public void setThriftPort(int thriftPort) {
        this.thriftPort = thriftPort;
    }

    @Required
    public void setProtobufPort(int protobufPort) {
        this.protobufPort = protobufPort;
    }
}
