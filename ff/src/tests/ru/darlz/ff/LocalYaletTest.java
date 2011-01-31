package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 31.01.11
 * Time: 9:36
 */
public class LocalYaletTest {
    @Test
    public void testPerformance() throws Exception {
        final long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; ++i) {
            InternalRequest req = new FakeInternalRequest();
            InternalResponse res = new FakeInternalResponse();
            Yalet y = new GoBackYalet();
            y.process(req, res);
        }

        final long processingTime = System.currentTimeMillis() - startTime;
        System.out.println("local yalet: " + processingTime + "ms");
    }
}
