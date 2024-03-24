package edu.java.services;

import edu.java.model.Chat;
import java.util.List;

public interface ChatService {
    void addChat(Chat chat);

    void deleteChat(Chat chat);

    boolean isChatExists(long id);

    List<Long> findAllChatsIdsWithLink(long linkId);
}
