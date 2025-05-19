package com.example.twitter.lambda;

import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.twitter.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            String username = objectMapper.readTree(input.getBody()).get("username").asText();
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(username);
            
            return response
                .withStatusCode(200)
                .withBody(objectMapper.writeValueAsString(user));
        } catch (Exception e) {
            return response
                .withStatusCode(500)
                .withBody("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}