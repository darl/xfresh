package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 30.01.11
 * Time: 0:48
 */
public class GoBackYalet implements Yalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        if (req.getHeader("Referer") != null)
            res.redirectTo(req.getHeader("Referer"));
        else
            res.redirectTo("/");
    }
}
