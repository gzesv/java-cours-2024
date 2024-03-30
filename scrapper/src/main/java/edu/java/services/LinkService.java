package edu.java.services;

import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    List<Link> getLinks(long chatId);

    Link add(long chatId, Link link);

    Link remove(long chatId, Link link);

    List<Link> findAllOutdatedLinks(long interval);

    void setUpdateAndCheckTime(long linkId, OffsetDateTime lastUpdateTime, OffsetDateTime lastCheckTime);
}
