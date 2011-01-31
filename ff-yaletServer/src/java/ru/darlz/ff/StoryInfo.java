package ru.darlz.ff;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: darl
 * Date: 30.01.11
 * Time: 19:26
 */
public class StoryInfo {
    public long getId() {
        return id;
    }

    public long getAuthorID() {
        return authorID;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public Date getDate() {
        return date;
    }

    public int getLikeIt() {
        return likeIt;
    }

    public boolean isLiked() {
        return liked;
    }

    private long id;
    private long authorID;
    private String description;
    private String body;
    private Date date;
    private int likeIt;   //count
    private boolean liked;

    public StoryInfo(long id, long authorID, String description, String body, Date date, int likeIt, boolean liked) {
        this.id = id;
        this.authorID = authorID;
        this.description = description;
        this.body = body;
        this.date = date;
        this.likeIt = likeIt;
        this.liked = liked;
    }

}
