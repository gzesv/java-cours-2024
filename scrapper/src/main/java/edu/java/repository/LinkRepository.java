package edu.java.repository;

import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Link add(Link link);

    Optional<Link> findLinkByUrl(String url);

    void remove(long linkId);

    List<Link> findAll(long chatId);

    List<Link> findAllOutdatedLinks(long interval);

    void setUpdateAndCheckTime(long id, OffsetDateTime lastUpdateTime, OffsetDateTime lastCheckTime);
}
