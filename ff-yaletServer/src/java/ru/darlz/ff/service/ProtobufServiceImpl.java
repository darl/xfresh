package ru.darlz.ff.service;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 03.02.11
 * Time: 0:21
 */
public class ProtobufServiceImpl implements ProtobufService.Service.BlockingInterface {
    @Override
    public ProtobufService.Response addStory(RpcController controller, ProtobufService.AddStoryRequest request) throws ServiceException {
        _serv.addStory(request.getMyId(), request.getDescription(), request.getBody());
        return ProtobufService.Response.getDefaultInstance();
    }

    @Override
    public ProtobufService.Response getStories(RpcController controller, ProtobufService.StoryRequest request) throws ServiceException {
        return ProtobufService.Response.newBuilder()
                .addAllList(
                        _serv.getStories(request.getUserId(), request.getBest(), request.getPage(), request.getMyId(), STORY_MAPPER)
                )
                .build();
    }

    @Override
    public ProtobufService.Response getLiked(RpcController controller, ProtobufService.StoryRequest request) throws ServiceException {
        return ProtobufService.Response.newBuilder()
                .addAllList(
                        _serv.getLiked(request.getUserId(), request.getBest(), request.getPage(), request.getMyId(), STORY_MAPPER)
                )
                .build();
    }

    @Override
    public ProtobufService.Response likeIt(RpcController controller, ProtobufService.LikeRequest request) throws ServiceException {
        _serv.likeIt(request.getMyId(), request.getStoryId());
        return ProtobufService.Response.getDefaultInstance();
    }

    @Override
    public ProtobufService.Story getStory(RpcController controller, ProtobufService.OneStoryRequest request) throws ServiceException {
        return _serv.getStory(request.getStoryId(), request.getMyId(), STORY_MAPPER);
    }

    @Override
    public ProtobufService.LikedResponse likedBy(RpcController controller, ProtobufService.LikedRequest request) throws ServiceException {
        return ProtobufService.LikedResponse.newBuilder()
                .addAllUserId(
                        _serv.likedBy(request.getStoryId(), new ParameterizedRowMapper<Long>() {
                            @Override
                            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                                return resultSet.getLong("by_user_id");
                            }
                        })
                )
                .build();
    }


    public ProtobufServiceImpl(ServiceImpl serv) {
        _serv = serv;
    }

    private ServiceImpl _serv;

    protected static ParameterizedRowMapper<ProtobufService.Story> STORY_MAPPER = new ParameterizedRowMapper<ProtobufService.Story>() {
        public ProtobufService.Story mapRow(final ResultSet rs, final int i) throws SQLException {
            return ProtobufService.Story.newBuilder()
                    .setStoryId(rs.getInt("story_id"))
                    .setAuthorId(rs.getLong("author_id"))
                    .setDescription(rs.getString("description"))
                    .setBody(rs.getString("body"))
                    .setDate(rs.getString("date"))
                    .setLikedBy(rs.getInt("_like"))
                    .setLiked(rs.getInt("_by_me") == 0).build();
        }
    };
}
