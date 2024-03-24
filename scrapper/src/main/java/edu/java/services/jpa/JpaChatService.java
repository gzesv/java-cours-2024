package edu.java.services.jpa;

import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.model.Chat;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.services.ChatService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatRepository chatRepository;

    @Override
    @Transactional
    public void addChat(Chat chat) {
        if (isChatExists(chat.getId())) {
            throw new ChatAlreadyExistsException();
        }

        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void deleteChat(Chat chat) {
        if (!isChatExists(chat.getId())) {
            throw new ChatNotFoundException();
        }

        chatRepository.delete(chat);
    }

    @Override
    public boolean isChatExists(long id) {
        return chatRepository.existsById(id);
    }

    @Override
    public List<Long> findAllChatsIdsWithLink(long linkId) {
        return chatRepository.findAllChatsIdsWithLink(linkId);
    }
}
