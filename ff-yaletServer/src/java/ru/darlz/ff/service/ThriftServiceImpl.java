package ru.darlz.ff.service;

import org.apache.thrift.TException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 02.02.11
 * Time: 22:51
 */
public class ThriftServiceImpl implements ThriftService.Iface {
    @Override
    public void addStory(long userId, String description, String body) throws TException {
        _serv.addStory(userId, description, body);
    }

    @Override
    public List<Story> getStories(long userId, boolean best, int page, long myId) throws TException {
        return _serv.getStories(userId, best, page, myId, STORY_MAPPER);
    }

    @Override
    public List<Story> getLiked(long userId, boolean best, int page, long myId) throws TException {
        return _serv.getLiked(userId, best, page, myId, STORY_MAPPER);
    }

    @Override
    public void likeIt(long userId, int storyId) throws TException {
        _serv.likeIt(userId, storyId);
    }

    @Override
    public Story getStory(int storyId, long myId) throws TException {
        return _serv.getStory(storyId, myId, STORY_MAPPER);
    }

    @Override
    public List<Long> likedBy(int storyId) throws TException {
        return _serv.likedBy(storyId, new ParameterizedRowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("by_user_id");
            }
        });
    }

    public ThriftServiceImpl(ServiceImpl serv) {
        _serv = serv;
    }

    private ServiceImpl _serv;

    protected static ParameterizedRowMapper<Story> STORY_MAPPER = new ParameterizedRowMapper<Story>() {
        public Story mapRow(final ResultSet rs, final int i) throws SQLException {
            return new Story(
                    rs.getInt("story_id")
                    , rs.getLong("author_id")
                    , rs.getString("description")
                    , rs.getString("body")
                    , rs.getString("date")
                    , rs.getInt("_like")
                    , rs.getInt("_by_me") == 0);
        }
    };
}
