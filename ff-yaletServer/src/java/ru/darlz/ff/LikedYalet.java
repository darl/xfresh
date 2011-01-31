package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 30.01.11
 * Time: 22:48
 */
public class LikedYalet extends StoryYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        int page = req.getIntParameter("page", 0);
        boolean best = req.getParameter("best") != null;
        if (best)
            res.add(Xmler.tag("best"));

        String ordBy = best ? "_like" : "s.date";
        Long user;
        try {
            user = Long.parseLong(req.getParameter("user"));
        } catch (NumberFormatException e) {
            user = req.getUserId();
        }
        if (user == null || user == 0) {
            res.add(Xmler.tag("invalid-user"));
            return;
        }

        final List<StoryInfo> stories = jdbcTemplate.query(
                "select s.story_id, s.author_id, s.description, s.body, s.date, count(l.by_user_id) as _like, min(abs(l.by_user_id-?)) as _by_me " +
                        "from stories as s " +
                        "left outer join likes as l on (s.story_id = l.story_id) " +
                        "group by s.story_id " +
                        "having min(abs(l.by_user_id-?)) = 0 " +
                        "order by " + ordBy + " DESC " +
                        "limit ?, ?"
                , STORY_MAPPER
                , req.getUserId()
                , user
                , page * ITEMS_PER_PAGE
                , ITEMS_PER_PAGE);

        if (stories.isEmpty()) {
            res.add(Xmler.tag("empty"));
            return;
        }

        res.add(Xmler.tag("user", String.valueOf(user)));
        res.add(stories);
    }
}
