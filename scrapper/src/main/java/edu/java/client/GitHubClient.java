package edu.java.client;

import edu.java.dto.github.RepositoryResponse;

public interface GitHubClient {
    RepositoryResponse fetchRepository(String user, String repository);
}
