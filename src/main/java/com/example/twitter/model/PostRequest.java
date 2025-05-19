package com.example.twitter.model;

public class PostRequest {
    private String username;
    private String text;

    // Constructor
    public PostRequest() {}

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "PostRequest{username='" + username + "', text='" + text + "'}";
    }
}
