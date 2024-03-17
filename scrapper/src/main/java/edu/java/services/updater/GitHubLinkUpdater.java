package edu.java.services.updater;

import edu.java.dto.Update;
import edu.java.model.Link;
import edu.java.services.LinkUpdater;
import java.util.Optional;

public class GitHubLinkUpdater implements LinkUpdater {
    private static final String SUPPORT_DOMAIN = "github.com";

    @Override
    public Optional<Update> fetchUpdate(Link link) {
        return Optional.empty();
    }

    @Override
    public String getSupportDomain() {
        return SUPPORT_DOMAIN;
    }
}
