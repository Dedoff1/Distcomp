package by.bsuir.mapper;

import by.bsuir.dto.TweetRequestTo;
import by.bsuir.dto.TweetResponseTo;
import by.bsuir.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    Tweet tweetRequestToTweet (TweetRequestTo tweetRequestTo);
    @Mapping(target = "userId", source = "tweet.user.id")
    TweetResponseTo tweetToTweetResponse(Tweet tweet);
}
