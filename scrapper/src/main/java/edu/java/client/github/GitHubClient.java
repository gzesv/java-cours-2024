package edu.java.client.github;

import edu.java.dto.github.RepositoryResponse;

public interface GitHubClient {
    RepositoryResponse fetchRepository(String user, String repository);
}
