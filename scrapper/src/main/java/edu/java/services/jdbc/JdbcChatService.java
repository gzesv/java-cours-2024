package edu.java.services.jdbc;

import edu.java.domain.ChatRepository;
import edu.java.domain.ChatToLinkRepository;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.model.Chat;
import edu.java.services.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
