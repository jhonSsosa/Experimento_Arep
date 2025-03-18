package com.example.twitter.model;

public class Post {
    private String id;
    private String text;
    private User user;


    public Post(String id, String text, User user) {
        if(text.length() > 140){
            throw new IllegalArgumentException("No puede excederse de más de 140 caracteres");
        }
        this.id = id;
        this.text = text;
        this.user = user;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
