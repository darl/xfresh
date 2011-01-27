package ru.darlz.ff;

import net.sf.xfresh.core.SelfWriter;
import net.sf.xfresh.util.XmlUtil;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 5:04
 */
public class dataWriter implements SelfWriter {
    @Override
    public void writeTo(ContentHandler handler) {
        try {
            XmlUtil.text(handler, _data);
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private String _data;

    public dataWriter(String _data) {
        this._data = _data;
    }
}
