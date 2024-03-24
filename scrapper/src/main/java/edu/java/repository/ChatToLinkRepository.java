package edu.java.repository;

import java.util.List;

public interface ChatToLinkRepository {
    void add(long chatId, long linkId);

    void remove(long chatId, long linkId);

    List<Long> findAllChatIdsWithLink(long linkId);
}
