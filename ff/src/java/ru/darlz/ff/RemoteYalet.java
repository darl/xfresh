package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 26.01.11
 * Time: 1:51
 */
public interface RemoteYalet {
    public abstract void process(final InternalRequest req, final InternalResponse res);

}
