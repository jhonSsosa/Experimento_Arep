package com.example.twitter.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.twitter.model.Post;
import com.example.twitter.model.PostRequest;
import com.example.twitter.model.Stream;
import com.example.twitter.model.User;
import com.example.twitter.service.TextAnalysisService;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

    private final Stream stream = new Stream();

    @Autowired
    private TextAnalysisService analysisService;

    @PostMapping(value = "/createPost", produces = "application/json;charset=UTF-8")
    public Post createPost(@RequestBody PostRequest postRequest) {
        System.out.println("Request body recibido: " + postRequest);

        // Crear usuario
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(postRequest.getUsername());

        // Ejecutar análisis
        Map<String, Object> analysisResult = analysisService.analyzeAsMap(postRequest.getText());
        System.out.println("Resultado del análisis: " + analysisResult);

        // Extraer los campos principales
        String etiqueta = (String) analysisResult.get("etiqueta");
        Double confianza = (Double) analysisResult.get("confianza");

        // Crear post con los datos completos
        Post post = new Post(
                UUID.randomUUID().toString(),
                postRequest.getText(),
                user,
                etiqueta,
                confianza
        );

        post.setAnalisisCompleto(analysisResult);

        stream.addPost(post);
        System.out.println("Post creado: " + post);

        return post;
    }

    @GetMapping("/getPosts")
    public List<Post> getPosts() {
        return stream.getPosts();
    }
}
