namespace java ru.darlz.ff.service

struct Story {
    1: i32 storyId,
    2: i64 authorId,
    3: string description,
    4: string body,
    5: string date,
    6: i32 likedBy,
    7: bool liked;
}


service ThriftService {
    void addStory(1: i64 myId, 2: string description, 3: string body),
    list<Story> getStories(1: i64 userId, 2: bool best, 3: i32 page, 4: i64 myId),
    list<Story> getLiked(1: i64 userId, 2: bool best, 3: i32 page, 4: i64 myId),
    void likeIt(1: i64 myId, 2: i32 storyId),
    Story getStory(1: i32 storyId, 2: i64 myId),
    list<i64> likedBy(1: i32 storyId)
}