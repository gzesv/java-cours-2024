package edu.java.services.jdbc;

import edu.java.domain.ChatRepository;
import edu.java.domain.ChatToLinkRepository;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
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
    public void addChat(long id) {
        if (isChatExists(id)) {
            throw new ChatAlreadyExistsException();
        }

        chatRepository.add(id);
    }

    @Override
    @Transactional
    public void deleteChat(long id) {
        if (!isChatExists(id)) {
            throw new ChatNotFoundException();
        }

        chatRepository.remove(id);
    }

    public boolean isChatExists(long id) {
        return chatRepository.findById(id).isPresent();
    }

    @Override
    public List<Long> findAllChatsIdsWithLink(long linkId) {
        return chatToLinkRepository.findAllChatIdsWithLink(linkId);
    }
}
