package com.example.twitter.model;

import java.util.ArrayList;
import java.util.List;

public class Stream {

    private static final List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

}
