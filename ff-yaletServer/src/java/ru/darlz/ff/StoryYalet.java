package ru.darlz.ff;

import net.sf.xfresh.db.AbstractDbYalet;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 30.01.11
 * Time: 20:54
 */
public abstract class StoryYalet extends AbstractDbYalet {
    protected final int ITEMS_PER_PAGE = 10;

    protected static ParameterizedRowMapper<StoryInfo> STORY_MAPPER = new ParameterizedRowMapper<StoryInfo>() {
        public StoryInfo mapRow(final ResultSet rs, final int i) throws SQLException {
            return new StoryInfo(
                    rs.getLong("story_id")
                    , rs.getLong("author_id")
                    , rs.getString("description")
                    , rs.getString("body")
                    , rs.getTimestamp("date")
                    , rs.getInt("_like")
                    , rs.getInt("_by_me") == 0
            );
        }
    };
}
