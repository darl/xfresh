package ru.darlz.ff;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.xml.Xmler;
import net.sf.xfresh.db.AbstractDbYalet;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 31.01.11
 * Time: 0:02
 */
public class AddStoryYalet extends AbstractDbYalet {
    @Override
    public void process(InternalRequest req, InternalResponse res) {
        Long author_id = req.getUserId();
        if (author_id == null || author_id == 0) {
            res.add(Xmler.tag("invalid-user"));
            return;
        }
        String description = req.getParameter("descr");
        String body = req.getParameter("body");
        if (description == null || body == null) {
            res.add(Xmler.tag("bad-params"));
            return;
        }

        try {
            jdbcTemplate.update(
                    "insert into stories (author_id, description, body, date) " +
                            "values (?,?,?,?)",
                    author_id,
                    description,
                    body,
                    new Date()
            );
        } catch (Exception e) {
            res.add(Xmler.tag("error"));
        }
        res.add(Xmler.tag("success"));
        res.redirectTo("/");
    }
}
