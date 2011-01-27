package ru.darlz.ff;

import net.sf.xfresh.core.*;
import net.sf.xfresh.ext.AuthHandler;
import net.sf.xfresh.ext.ExtYaletFilter;
import net.sf.xfresh.util.XmlUtil;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import ru.darlz.ff.thrift.RemoteThriftYalet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 18:58
 */
public class RemoteYaletFilter extends ExtYaletFilter {
    private static final String XFRESH_REMOTE_YALET_URI = "ryalet";
    private static final String YALET_ELEMENT = "Yalet";
    private static final String XFRESH_EXT_URI = "http://xfresh.sf.net/ext";
    private String rYaletName;
    private String rYaletHost;
    private String rYaletPort;
    private String rYaletType;
    private static final String DATA_ELEMENT = "data";
    private static final String ERRORS_ELEMENT = "errors";
    private static final String ID_ATTRIBUTE = "id";
    private static final String PROCESSING_TIME_ATTRIBUTE = "processing-time";
    private static final String THRIFT = "thrift";
    private static final String PROTOBUF = "protobuf";
    private static final String RMI = "rmi";

    public RemoteYaletFilter(final SingleYaletProcessor singleYaletProcessor, final AuthHandler authHandler, final InternalRequest request, final InternalResponse response, final String resourceBase) {
        super(singleYaletProcessor, authHandler, request, response, resourceBase);
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (isRemoteYaletBlock(uri, localName)) {
            rYaletName = atts.getValue("id").trim();
            rYaletHost = atts.getValue("host").trim();
            rYaletPort = atts.getValue("port").trim();
            rYaletType = atts.getValue("type").trim();
        } else {
            super.startElement(uri, localName, qName, atts);
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (isRemoteYaletBlock(uri, localName)) {
            processYalet();
        } else {
            super.endElement(uri, localName, qName);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    private void processYalet() throws SAXException {
        RemoteYalet yal = null;
        if (rYaletType.equalsIgnoreCase(THRIFT)) {
            yal = new RemoteThriftYalet();
            yal.setRemoteName(rYaletName + YALET_ELEMENT);
            ((RemoteThriftYalet) yal).setHost(rYaletHost);
            ((RemoteThriftYalet) yal).setPort(Integer.parseInt(rYaletPort));

        } else if (rYaletType.equalsIgnoreCase(PROTOBUF)) {
            //todo
        } else if (rYaletType.equalsIgnoreCase(RMI)) {
            //todo
        } else
            throw new YaletResolvingException(rYaletName);

        if (yal == null) return;

        final long startTime = System.currentTimeMillis();
        yal.process(request, response);
        final long processingTime = System.currentTimeMillis() - startTime;

        XmlUtil.start(getContentHandler(), DATA_ELEMENT, ID_ATTRIBUTE, rYaletName, PROCESSING_TIME_ATTRIBUTE, Long.toString(processingTime));

        writeData();

        if (!response.getErrors().isEmpty()) {
            SaxGenerator saxGenerator = new DefaultSaxGenerator();//todo: DI
            XmlUtil.start(getContentHandler(), ERRORS_ELEMENT, ID_ATTRIBUTE, rYaletName);
            saxGenerator.writeXml(getContentHandler(), response.getErrors());
            XmlUtil.end(getContentHandler(), ERRORS_ELEMENT);
        }

        response.clear();
        XmlUtil.end(getContentHandler(), DATA_ELEMENT);
    }

    private void writeData() throws SAXException {
        try {
            final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setXIncludeAware(true);
            final SAXParser saxParser = parserFactory.newSAXParser();
            final XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
            xmlReader.setContentHandler(getContentHandler());

            List<Object> data = response.getData();

            for (Object s : data) {
                if (s instanceof String) {
                    InputStream content = new ByteArrayInputStream(((String) s).getBytes());
                    xmlReader.parse(new InputSource(content));
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();//ignore
        } catch (IOException e) {
            //ignore
        }
    }


    private boolean isRemoteYaletBlock(String uri, String localName) {
        return XFRESH_EXT_URI.equalsIgnoreCase(uri) && XFRESH_REMOTE_YALET_URI.equalsIgnoreCase(localName);
    }
}
