package edu.java.configuration;

import edu.java.client.github.GitHubClient;
import edu.java.client.github.GitHubWebClient;
import edu.java.client.stackoverflow.StackOverflowClient;
import edu.java.client.stackoverflow.StackOverflowWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        return new StackOverflowWebClient(applicationConfig.clientBaseUrl().stackoverflowUrl());
    }

    @Bean
    public GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        return new GitHubWebClient(applicationConfig.clientBaseUrl().githubUrl());
    }
}
