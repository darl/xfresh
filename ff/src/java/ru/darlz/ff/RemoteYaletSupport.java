package ru.darlz.ff;

import net.sf.xfresh.core.DefaultYaletSupport;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.springframework.beans.factory.annotation.Required;
import org.xml.sax.XMLFilter;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 21:14
 */
public class RemoteYaletSupport extends DefaultYaletSupport {

    private String resourceBase;

    @Required
    public void setResourceBase(final String resourceBase) {
        this.resourceBase = resourceBase;
    }

    @Override
    public XMLFilter createFilter(final InternalRequest request, final InternalResponse response) {
        return new RemoteYaletFilter(singleYaletProcessor, authHandler, request, response, resourceBase);
    }
}
