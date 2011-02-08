package ru.darlz.ff.service;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 02.02.11
 * Time: 22:05
 */
public class ServiceImpl {
    protected SimpleJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addStory(long userId, String description, String body) {
        jdbcTemplate.update(
                "insert into stories (author_id, description, body, date) " +
                        "values (?,?,?,?)",
                userId,
                description,
                body,
                new Date()
        );
    }

    public <T> List<T> getStories(long userId, boolean best, int page, long myId, ParameterizedRowMapper<T> rm) {
        String ordBy = best ? "_like" : "s.date";
        String where = "";
        if (userId != -1)
            where = "where s.author_id = " + userId + " ";
        return jdbcTemplate.query(
                "select s.story_id, s.author_id, s.description, s.body, s.date, count(l.by_user_id) as _like, min(abs(l.by_user_id-?)) as _by_me " +
                        "from stories as s " +
                        "left outer join likes as l on (s.story_id = l.story_id) " +
                        where +
                        "group by s.story_id " +
                        "order by " + ordBy + " DESC " +
                        "limit ?, ?"
                , rm
                , myId
                , page * 10
                , 10);
    }

    public <T> List<T> getLiked(long userId, boolean best, int page, long myId, ParameterizedRowMapper<T> rm) {
        String ordBy = best ? "_like" : "s.date";
        return jdbcTemplate.query(
                "select s.story_id, s.author_id, s.description, s.body, s.date, count(l.by_user_id) as _like, min(abs(l.by_user_id-?)) as _by_me " +
                        "from stories as s " +
                        "left outer join likes as l on (s.story_id = l.story_id) " +
                        "group by s.story_id " +
                        "having min(abs(l.by_user_id-?)) = 0 " +
                        "order by " + ordBy + " DESC " +
                        "limit ?, ?"
                , rm
                , myId
                , userId
                , page * 10
                , 10);
    }

    public void likeIt(long userId, int storyId) {
        if (jdbcTemplate.queryForInt("select count(*) from stories where story_id = ?", storyId) == 0) {
            return;
        }
        if (jdbcTemplate.queryForInt("select count(*) from likes where story_id= ? and by_user_id= ?", storyId, userId) > 0) {
            return;
        }

        jdbcTemplate.update(
                "insert into likes (story_id, by_user_id) " +
                        "values (?,?)",
                storyId,
                userId);
    }

    public <T> T getStory(int storyId, long myId, ParameterizedRowMapper<T> rm) {
        return jdbcTemplate.queryForObject(
                "select s.story_id, s.author_id, s.description, s.body, s.date, count(l.by_user_id) as _like, min(abs(l.by_user_id-?)) as _by_me " +
                        "from stories as s " +
                        "left outer join likes as l on (s.story_id = l.story_id) " +
                        "where s.story_id = ? " +
                        "group by s.story_id "
                , rm
                , myId
                , storyId);
    }

    public <T> List<T> likedBy(int storyId, ParameterizedRowMapper<T> rm) {
        return jdbcTemplate.query(
                "select * from likes where story_id = ?"
                , rm
                , storyId);
    }
}
