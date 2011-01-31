package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.db.AbstractDbYalet;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 31.01.11
 * Time: 0:19
 */
public class LikeItYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        Long user_id = req.getUserId();
        if (user_id == null || user_id == 0) {
            res.add(Xmler.tag("invalid-user"));
            return;
        }
        int story_id = req.getIntParameter("story", -1);
        if (jdbcTemplate.queryForInt("select count(*) from stories where story_id = ?", story_id) == 0) {
            res.add(Xmler.tag("invalid-story"));
            return;
        }
        if (jdbcTemplate.queryForInt("select count(*) from likes where story_id= ? and by_user_id= ?", story_id, user_id) > 0) {
            res.add(Xmler.tag("already-voted"));
            return;
        }

        try {
            jdbcTemplate.update(
                    "insert into likes (story_id, by_user_id) " +
                            "values (?,?)",
                    story_id,
                    user_id
            );
        } catch (Exception e) {
            res.add(Xmler.tag("error"));
        }
    }
}
