package com.example.twitter.lambda;

import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.twitter.model.Post;
import com.example.twitter.model.PostRequest;
import com.example.twitter.model.User;
import com.example.twitter.service.TextAnalysisService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TextAnalysisService textAnalysisService = new TextAnalysisService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            // Deserializar el cuerpo de la solicitud
            PostRequest postRequest = objectMapper.readValue(input.getBody(), PostRequest.class);

            // Llamada al servicio de análisis de texto
            Map<String, Object> analysisResult = textAnalysisService.analyzeAsMap(postRequest.getText());

            String etiqueta = (String) analysisResult.get("etiqueta");
            Double confianza = (Double) analysisResult.get("confianza");

            // Crear el objeto User (simulado por la función callUserService)
            User user = objectMapper.readValue(callUserService(postRequest.getUsername()), User.class);

            // Crear el objeto Post con la etiqueta y confianza
            Post post = new Post(
                    UUID.randomUUID().toString(),
                    postRequest.getText(),
                    user,
                    etiqueta,  // Aquí pasamos la etiqueta
                    confianza  // Y la confianza
            );

            // Devolver la respuesta con el post creado
            return response
                    .withStatusCode(200)
                    .withBody(objectMapper.writeValueAsString(post));
        } catch (Exception e) {
            // Manejar errores
            return response
                    .withStatusCode(500)
                    .withBody("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // Simula una llamada al servicio de usuario
    private String callUserService(String username) {
        return "{\"id\":\"" + UUID.randomUUID().toString() + "\",\"username\":\"" + username + "\"}";
    }
}

