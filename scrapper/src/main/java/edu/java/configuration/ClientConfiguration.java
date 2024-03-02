package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.GitHubWebClient;
import edu.java.client.impl.StackOverflowWebClient;
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
