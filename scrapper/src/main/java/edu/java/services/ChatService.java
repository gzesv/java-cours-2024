package edu.java.services;

import java.util.List;

public interface ChatService {
    void addChat(long id);

    void deleteChat(long id);

    boolean isChatExists(long id);

    List<Long> findAllChatsIdsWithLink(long linkId);
}
