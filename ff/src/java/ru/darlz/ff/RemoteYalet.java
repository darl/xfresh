package ru.darlz.ff;

import net.sf.xfresh.core.Yalet;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 26.01.11
 * Time: 1:51
 */
public abstract class RemoteYalet implements Yalet {
    @Required
    public void setRemoteName(String _yaletName) {
        this._yaletName = _yaletName;
    }

    protected String _yaletName;
}
