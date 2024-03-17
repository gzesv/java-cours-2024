package edu.java.domain;

import java.util.List;

public interface ChatToLinkRepository {
    void add(long chatId, long linkId);

    void remove(long chatId, long linkId);

    List<Long> linkTrackers(long linkId);
}
