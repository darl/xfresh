package net.sf.xfresh.server;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

/**
 * Date: 21.04.2007
 * Time: 15:38:54
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.ru
 */
public class DoNothingYalet implements Yalet {
    public void process(final InternalRequest req, final InternalResponse res) {
        // do nothing
    }
}
