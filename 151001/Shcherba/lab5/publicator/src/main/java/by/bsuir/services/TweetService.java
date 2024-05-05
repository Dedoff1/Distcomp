package by.bsuir.services;

import by.bsuir.dto.TweetRequestTo;
import by.bsuir.dto.TweetResponseTo;
import by.bsuir.entities.Tweet;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.DuplicationException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.mapper.TweetListMapper;
import by.bsuir.mapper.TweetMapper;
import by.bsuir.repository.UserRepository;
import by.bsuir.repository.TweetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@CacheConfig(cacheNames = "tweetCache")
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;
    @Autowired
    TweetRepository tweetDao;
    @Autowired
    TweetListMapper tweetListMapper;
    @Autowired
    UserRepository userRepository;
    @Cacheable(value = "tweets", key = "#id", unless = "#result == null")
    public TweetResponseTo getTweetById(@Min(0) Long id) throws NotFoundException {
        Optional<Tweet> tweet = tweetDao.findById(id);
        return tweet.map(value -> tweetMapper.tweetToTweetResponse(value)).orElseThrow(() -> new NotFoundException("Tweet not found!", 40004L));
    }

    @Cacheable(cacheNames = "tweets")
    public List<TweetResponseTo> getTweets(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Tweet> tweets = tweetDao.findAll(pageable);
        return tweetListMapper.toTweetResponseList(tweets.toList());
    }
    @CacheEvict(cacheNames = "tweets", allEntries = true)
    public TweetResponseTo saveTweet(@Valid TweetRequestTo tweet) throws DuplicationException {
        Tweet tweetToSave = tweetMapper.tweetRequestToTweet(tweet);
        if (tweetDao.existsByTitle(tweetToSave.getTitle())) {
            throw new DuplicationException("Title duplication", 40005L);
        }
        if (tweet.getUserId() != null) {
            tweetToSave.setUser(userRepository.findById(tweet.getUserId()).orElseThrow(() -> new NotFoundException("User not found!", 40004L)));
        }
        return tweetMapper.tweetToTweetResponse(tweetDao.save(tweetToSave));
    }
    @Caching(evict = { @CacheEvict(cacheNames = "tweets", key = "#id"),
            @CacheEvict(cacheNames = "tweets", allEntries = true) })
    public void deleteTweet(@Min(0) Long id) throws DeleteException {
        if (!tweetDao.existsById(id)) {
            throw new DeleteException("Tweet not found!", 40004L);
        } else {
            tweetDao.deleteById(id);
        }
    }
    @CacheEvict(cacheNames = "tweets", allEntries = true)
    public TweetResponseTo updateTweet(@Valid TweetRequestTo tweet) throws UpdateException {
        Tweet tweetToUpdate = tweetMapper.tweetRequestToTweet(tweet);
        if (!tweetDao.existsById(tweet.getId())) {
            throw new UpdateException("Note not found!", 40004L);
        } else {
            if (tweet.getUserId() != null) {
                tweetToUpdate.setUser(userRepository.findById(tweet.getUserId()).orElseThrow(() -> new NotFoundException("User not found!", 40004L)));
            }
            return tweetMapper.tweetToTweetResponse(tweetDao.save(tweetToUpdate));
        }
    }

    public List<TweetResponseTo> getTweetByCriteria(List<String> markerName, List<Long> markerId, String userLogin, String title, String content) {
        return tweetListMapper.toTweetResponseList(tweetDao.findTweetsByParams(markerName, markerId, userLogin, title, content));
    }
}
