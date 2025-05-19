package com.example.twitter.model;

import java.util.Map;

public class Post {
    private String id;
    private String text;
    private User user;
    private Map<String, Object> analisisCompleto; // Nuevo campo

    public Post(String id, String text, User user, String etiqueta, Double confianza) {
        this.id = id;
        this.text = text;
        this.user = user;
    }

    // Getters y Setters
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


    public Map<String, Object> getAnalisisCompleto() {
        return analisisCompleto;
    }

    public void setAnalisisCompleto(Map<String, Object> analisisCompleto) {
        this.analisisCompleto = analisisCompleto;
    }
}
