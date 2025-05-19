package com.example.twitter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.twitter.controller.TwitterController;
import com.example.twitter.model.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class TwitterLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TwitterController twitterController = new TwitterController();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");

        try {
            String path = input.getPath();
            String httpMethod = input.getHttpMethod();

            if ("/twitter/getPosts".equals(path) && "GET".equals(httpMethod)) {
                String jsonResponse = objectMapper.writeValueAsString(twitterController.getPosts());
                return response
                        .withStatusCode(200)
                        .withHeaders(headers)
                        .withBody(jsonResponse);
            }
            else if ("/twitter/createPost".equals(path) && "POST".equals(httpMethod)) {
                PostRequest postRequest = objectMapper.readValue(input.getBody(), PostRequest.class);
                String jsonResponse = objectMapper.writeValueAsString(twitterController.createPost(postRequest));
                return response
                        .withStatusCode(200)
                        .withHeaders(headers)
                        .withBody(jsonResponse);
            }

            return response
                    .withStatusCode(404)
                    .withHeaders(headers)
                    .withBody("{\"error\": \"Path not found\"}");

        } catch (Exception e) {
            return response
                    .withStatusCode(500)
                    .withHeaders(headers)
                    .withBody("{\"error\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}
