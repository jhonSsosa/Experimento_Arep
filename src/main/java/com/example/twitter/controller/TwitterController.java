package com.example.twitter.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.twitter.model.Post;
import com.example.twitter.model.PostRequest;
import com.example.twitter.model.Stream;
import com.example.twitter.model.User;

@RestController
@RequestMapping("/twitter")
public class TwitterController {
    private final Stream stream = new Stream();

    @PostMapping("/createPost")
    public Post createPost(@RequestBody PostRequest postRequest) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(postRequest.getUsername());

        Post post = new Post(UUID.randomUUID().toString(), postRequest.getText(), user);
        stream.addPost(post);
        return post;
    }

    @GetMapping("/getPosts")
    public List<Post> getPosts() {
        return stream.getPosts();
    }

}