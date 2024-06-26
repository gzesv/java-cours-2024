package edu.java.services.jdbc;

import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import edu.java.repository.ChatToLinkRepository;
import edu.java.services.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final ChatRepository chatRepository;

    private final ChatToLinkRepository chatToLinkRepository;

    @Override
    @Transactional
    public void addChat(Chat chat) {
        if (isChatExists(chat.getId())) {
            throw new ChatAlreadyExistsException();
        }

        chatRepository.add(chat.getId());
    }

    @Override
    @Transactional
    public void deleteChat(Chat chat) {
        if (isChatNotExists(chat.getId())) {
            throw new ChatNotFoundException();
        }

        chatRepository.remove(chat.getId());
    }

    @Override
    public boolean isChatExists(long id) {
        return chatRepository.findById(id).isPresent();
    }

    @Override
    public boolean isChatNotExists(long id) {
        return chatRepository.findById(id).isEmpty();
    }

    @Override
    public List<Long> findAllChatsIdsWithLink(long linkId) {
        return chatToLinkRepository.findAllChatIdsWithLink(linkId);
    }
}
