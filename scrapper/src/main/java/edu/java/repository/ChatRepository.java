package edu.java.repository;

import edu.java.model.Chat;
import java.util.Optional;

public interface ChatRepository {
    Optional<Chat> findById(long chatId);

    void add(long id);

    void remove(long id);
}
