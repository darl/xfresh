package ru.darlz.ff.protobuf;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.YaletResolvingException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 29.01.11
 * Time: 5:14
 */
public class ProtobufYaletProcessorImpl implements RemoteYaletProcessor.Processor.BlockingInterface, ApplicationContextAware {
    @Override
    public RemoteYaletProcessor.RemoteInternalResponse process
            (RpcController controller, RemoteYaletProcessor.RemoteInternalRequest request) throws ServiceException {

        ProtobufInternalRequest _req = new ProtobufInternalRequest(request);
        ProtobufInternalResponse _res = new ProtobufInternalResponse(RemoteYaletProcessor.RemoteInternalResponse.newBuilder());
        String yalet = request.getYaletName();

        final Object bean = applicationContext.getBean(yalet);
        if (bean == null) {
            log.error("Can't find yalet by id: " + yalet);
            throw new YaletResolvingException(yalet);
        }
        if (!(bean instanceof Yalet)) {
            log.error("Illegal type (" + bean.getClass() + ") of bean with id: " + yalet);
            throw new YaletResolvingException(yalet);
        }

        log.info("Processing {" + yalet + "} yalet(ProtoBuf)");
        ((Yalet) bean).process(_req, _res);

        return _res.build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;
    private static final Logger log = Logger.getLogger(RemoteYaletProcessor.class);
}
