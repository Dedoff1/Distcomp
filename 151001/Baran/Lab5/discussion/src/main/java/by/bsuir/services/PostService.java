package by.bsuir.services;

import by.bsuir.dto.PostRequestTo;
import by.bsuir.dto.PostResponseTo;
import by.bsuir.entities.Post;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.NotFoundException;
import by.bsuir.exceptions.UpdateException;
import by.bsuir.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class PostService {
    @Autowired
    PostRepository postDao;

    public PostResponseTo getPostById(Long id) throws NotFoundException {
        Optional<Post> post = postDao.findById(id).stream().findFirst();
        return post.map(this::postToPostResponse).orElseThrow(() -> new NotFoundException("Post not found!", 40004L));
    }

    public List<PostResponseTo> getPosts() {
        return toPostResponseList(postDao.findAll());
    }

    public PostResponseTo savePost(@Valid PostRequestTo post, String country) {
        Post postToSave = postRequestToPost(post);
        postToSave.setId(getId());
        postToSave.setCountry(getCountry(country));
        return postToPostResponse(postDao.save(postToSave));
    }

    public void deletePost(Long id) throws DeleteException {
        Optional<Post> post = postDao.findById(id).stream().findFirst();
        if (post.isEmpty()) {
            throw new DeleteException("Post not found!", 40004L);
        } else {
            postDao.deleteByCountryAndIssueIdAndId(post.get().getCountry(), post.get().getIssueId(), post.get().getId());
        }
    }

    public PostResponseTo updatePost(@Valid PostRequestTo post, String country) throws UpdateException {
        Post postToUpdate = postRequestToPost(post);
        postToUpdate.setId(post.getId());
        postToUpdate.setCountry(getCountry(country));
        Optional<Post> postOptional = postDao.findById(post.getId()).stream().findFirst();
        if (postOptional.isEmpty()) {
            throw new UpdateException("Post not found!", 40004L);
        } else {
            postDao.deleteByCountryAndIssueIdAndId(postOptional.get().getCountry(), postOptional.get().getIssueId(), postOptional.get().getId());
            return postToPostResponse(postDao.save(postToUpdate));
        }
    }

    public List<PostResponseTo> getPostByIssueId(Long issueId) throws NotFoundException {
        List<Post> post = postDao.findByIssueId(issueId);
        if (post.isEmpty()) {
            throw new NotFoundException("Post not found!", 40004L);
        }
        return toPostResponseList(post);
    }

    private PostResponseTo postToPostResponse(Post post) {
        PostResponseTo postResponseTo = new PostResponseTo();
        postResponseTo.setIssueId(post.getIssueId());
        postResponseTo.setId(post.getId());
        postResponseTo.setContent(post.getContent());
        return postResponseTo;
    }

    private List<PostResponseTo> toPostResponseList(List<Post> posts) {
        List<PostResponseTo> response = new ArrayList<>();
        for (Post post : posts) {
            response.add(postToPostResponse(post));
        }
        return response;
    }

    private Post postRequestToPost(PostRequestTo requestTo) {
        Post post = new Post();
        post.setId(requestTo.getId());
        post.setContent(requestTo.getContent());
        post.setIssueId(requestTo.getIssueId());
        return post;
    }

    private long getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, postDao.countByCountry(country)*(1-languageMap.get(country)));
        }
        double minValue = Double.MAX_VALUE;
        String minKey = null;

        for (Map.Entry<String, Double> entry : loadMap.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    private static Map<String, Double> getStringDoubleMap(String requestHeader) {
        String[] languages = requestHeader.split(",");
        Map<String, Double> languageMap = new HashMap<>();
        for (String language : languages) {
            String[] parts = language.split(";");
            String lang = parts[0].trim();
            double priority = 1.0; // По умолчанию
            if (parts.length > 1) {
                String[] priorityParts = parts[1].split("=");
                priority = Double.parseDouble(priorityParts[1]);
            }
            languageMap.put(lang, priority);
        }
        return languageMap;
    }
}
