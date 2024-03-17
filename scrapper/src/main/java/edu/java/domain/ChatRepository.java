package edu.java.domain;

import edu.java.model.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    Optional<Chat> findById(long chatId);

    void add(long id);

    void remove(long id);

    List<Long> findAllChatIdsWithLink(long linkId);
}
