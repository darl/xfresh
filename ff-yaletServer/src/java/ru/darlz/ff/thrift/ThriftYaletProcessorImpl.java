package ru.darlz.ff.thrift;

import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.YaletResolvingException;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 14:21
 */
public class ThriftYaletProcessorImpl implements RemoteYaletProcessor.Iface, ApplicationContextAware {
    @Override
    public RemoteInternalResponse process(String yalet, RemoteInternalRequest req, RemoteInternalResponse res) throws TException {

        ThriftInternalRequest tReq = new ThriftInternalRequest(req);
        ThriftInternalResponse tRes = new ThriftInternalResponse(res);

        final Object bean = applicationContext.getBean(yalet);
        if (bean == null) {
            log.error("Can't find yalet by id: " + yalet);
            throw new YaletResolvingException(yalet);
        }
        if (!(bean instanceof Yalet)) {
            log.error("Illegal type (" + bean.getClass() + ") of bean with id: " + yalet);
            throw new YaletResolvingException(yalet);
        }

        log.info("Processing {" + yalet + "} yalet(Thrift)");
        ((Yalet) bean).process(tReq, tRes);

        return res;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;
    private static final Logger log = Logger.getLogger(RemoteYaletProcessor.class);
}
