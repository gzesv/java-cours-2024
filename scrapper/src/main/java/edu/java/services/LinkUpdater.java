package edu.java.services;

import edu.java.dto.Update;
import edu.java.model.Link;
import java.util.Optional;

public interface LinkUpdater {
    Optional<Update> fetchUpdate(Link link);

    String getSupportDomain();
}
