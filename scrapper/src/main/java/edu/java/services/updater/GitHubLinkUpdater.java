package edu.java.services.updater;

import edu.java.client.github.GitHubClient;
import edu.java.dto.Update;
import edu.java.dto.github.RepositoryResponse;
import edu.java.model.Link;
import edu.java.services.LinkUpdater;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubLinkUpdater implements LinkUpdater {
    private static final String SUPPORT_DOMAIN = "github.com";

    private final GitHubClient gitHubWebClient;

    private static final int userIndex = 2;

    private static final int repositoryIndex = 3;

    @Override
    public Optional<Update> fetchUpdate(Link link) {
        var repositoryData = getUserAndRepository(link.getUrl());

        RepositoryResponse response =
            gitHubWebClient.fetchRepository(repositoryData.getKey(), repositoryData.getValue());

        if (response.pushedAt().isAfter(link.getUpdateAt())) {
            return Optional.of(
                new Update(link.getId(),
                    link.getUrl(),
                    "Появился новый коммит",
                    response.pushedAt()
                )
            );
        }
        return Optional.empty();
    }

    @Override
    public String getSupportDomain() {
        return SUPPORT_DOMAIN;
    }

    private Pair<String, String> getUserAndRepository(String url) {
        String[] urlParts = url.split("/+");
        return Pair.of(urlParts[userIndex], urlParts[repositoryIndex]);
    }
}
