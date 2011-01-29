package net.sf.xfresh.auth.yalet;

import net.sf.xfresh.auth.UserInfo;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 30.01.11
 * Time: 1:24
 */
public class UsersYalet extends AbstractAuthYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        final List<UserInfo> users = authService.getUsers();

        for (UserInfo u : users)
            res.add(getUserTag(u));
    }
}
