package ru.darlz.ff.protobuf;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.junit.Test;
import ru.darlz.ff.FakeInternalRequest;
import ru.darlz.ff.FakeInternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 31.01.11
 * Time: 9:23
 */
public class RemoteProtobufYaletTest {
    @Test
    public void testPerformance() throws Exception {
        final long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; ++i) {
            InternalRequest req = new FakeInternalRequest();
            InternalResponse res = new FakeInternalResponse();
            RemoteProtobufYalet y = new RemoteProtobufYalet();
            y.setHost("localhost");
            y.setPort(33003);
            y.setRemoteName("goBackYalet");
            y.process(req, res);
        }

        final long processingTime = System.currentTimeMillis() - startTime;
        System.out.println("protobuf yalet: " + processingTime + "ms");
    }
}
