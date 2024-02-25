package edu.java.client.impl;

import edu.java.client.StackOverflowClient;
import edu.java.dto.stackoverflow.QuestionResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowWebClient implements StackOverflowClient {
    private static final String DEFAULT_BASE_URL = "https://api.stackexchange.com/2.3";
    private static final String QUESTION_URI = "/questions/{id}";
    private final WebClient webClient;

    public StackOverflowWebClient() {
        this(DEFAULT_BASE_URL);
    }

    public StackOverflowWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public QuestionResponse fetchQuestion(Long question) {
        return webClient
            .get()
            .uri(builder -> builder
                .path(QUESTION_URI)
                .queryParam("site", "stackoverflow")
                .build(question))
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .block();
    }
}
