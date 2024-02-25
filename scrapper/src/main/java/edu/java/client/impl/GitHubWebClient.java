package edu.java.client.impl;

import edu.java.client.GitHubClient;
import edu.java.dto.github.RepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubWebClient implements GitHubClient {
    private static final String DEFAULT_BASE_URL = "https://api.github.com";
    private static final String REPOSITORY_URI = "/repos/{owner}/{repo}";
    private final WebClient webClient;

    public GitHubWebClient() {
        this(DEFAULT_BASE_URL);
    }

    public GitHubWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public RepositoryResponse fetchRepository(String owner, String repository) {
        return webClient.get()
            .uri(REPOSITORY_URI, owner, repository)
            .retrieve()
            .bodyToMono(RepositoryResponse.class)
            .block();
    }
}
