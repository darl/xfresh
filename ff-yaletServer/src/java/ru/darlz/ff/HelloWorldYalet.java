package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 27.01.11
 * Time: 15:37
 */
public class HelloWorldYalet implements Yalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        res.add("Hello, World!");
        res.add(req.getHeader("User-Agent"));
    }
}
