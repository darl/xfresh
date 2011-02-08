package ru.darlz.ff.service;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 02.02.11
 * Time: 18:51
 */
public class ProtobufProxy {
    private ProtobufService.Service.BlockingInterface stub;
    private RpcController rpcController;

    public ProtobufProxy(ProtobufService.Service.BlockingInterface stub, RpcController rpcController) {
        this.stub = stub;
        this.rpcController = rpcController;
    }

    public void addStory(long userId, String description, String body) throws ServiceException {
        stub.addStory(rpcController, ProtobufService.AddStoryRequest.newBuilder()
                .setMyId(userId)
                .setDescription(description)
                .setBody(body).build());
    }

    public List<Story> getStories(long userId, boolean best, int page, long myId) throws ServiceException {
        List<ProtobufService.Story> list = stub.getStories(rpcController, ProtobufService.StoryRequest.newBuilder()
                .setUserId(userId)
                .setBest(best)
                .setMyId(myId)
                .setPage(page).build()).getListList();
        List<Story> res = new ArrayList<Story>();
        for (ProtobufService.Story st : list) {
            res.add(new Story(st.getStoryId(),
                    st.getAuthorId(),
                    st.getDescription(),
                    st.getBody(),
                    st.getDate(),
                    st.getLikedBy(),
                    st.getLiked()));
        }
        return res;
    }

    public List<Story> getLiked(long userId, boolean best, int page, long myId) throws ServiceException {
        List<ProtobufService.Story> list = stub.getLiked(rpcController, ProtobufService.StoryRequest.newBuilder()
                .setUserId(userId)
                .setBest(best)
                .setMyId(myId)
                .setPage(page).build()).getListList();
        List<Story> res = new ArrayList<Story>();
        for (ProtobufService.Story st : list) {
            res.add(new Story(st.getStoryId(),
                    st.getAuthorId(),
                    st.getDescription(),
                    st.getBody(),
                    st.getDate(),
                    st.getLikedBy(),
                    st.getLiked()));
        }
        return res;
    }

    public void likeIt(long userId, int storyId) throws ServiceException {
        stub.likeIt(rpcController, ProtobufService.LikeRequest.newBuilder()
                .setMyId(userId)
                .setStoryId(storyId).build());
    }

    public Story getStory(int storyId, long myId) throws ServiceException {
        ProtobufService.Story res = stub.getStory(rpcController, ProtobufService.OneStoryRequest.newBuilder()
                .setStoryId(storyId)
                .setMyId(myId).build());
        return new Story(res.getStoryId(),
                res.getAuthorId(),
                res.getDescription(),
                res.getBody(),
                res.getDate(),
                res.getLikedBy(),
                res.getLiked());
    }

    public List<Long> likedBy(int storyId) throws ServiceException {
        return stub.likedBy(rpcController, ProtobufService.LikedRequest.newBuilder()
                .setStoryId(storyId).build()).getUserIdList();
    }


}
