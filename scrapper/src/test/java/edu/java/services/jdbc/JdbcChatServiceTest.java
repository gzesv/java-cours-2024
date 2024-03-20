package edu.java.services.jdbc;

import edu.java.domain.ChatRepository;
import edu.java.domain.ChatToLinkRepository;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.model.Chat;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private ChatToLinkRepository chatToLinkRepository;

    @InjectMocks
    private JdbcChatService chatService;

    @Test
    void addChatTest() {
        chatService.addChat(1L);

        Mockito.verify(chatRepository).add(1L);
    }

    @Test
    void addChatWhenChatExistsTest() {
        Chat chat = new Chat(1L);
        when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));

        assertThatExceptionOfType(ChatAlreadyExistsException.class)
            .isThrownBy(() -> chatService.addChat(1L));
    }

    @Test
    public void deleteChatTest() {
        Chat chat = new Chat(1L);
        when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));

        chatService.deleteChat(chat.getId());

        Mockito.verify(chatRepository).remove(chat.getId());
    }

    @Test
    public void deleteChatWhenChatNotExistsTest() {
        Chat chat = new Chat(1L);
        assertThatExceptionOfType(ChatNotFoundException.class)
            .isThrownBy(() -> chatService.deleteChat(chat.getId()));
    }

    @Test
    void isChatExists() {
        Chat chat = new Chat(1L);

        chatService.isChatExists(chat.getId());

        Mockito.verify(chatRepository).findById(chat.getId());
    }

    @Test
    void findAllChatsIdsWithLink() {
        Chat chat = new Chat(1L);
        long testLinkId = 123;
        when(chatToLinkRepository.findAllChatIdsWithLink(testLinkId)).thenReturn(List.of(chat.getId()));

        List<Long> chatIds = chatService.findAllChatsIdsWithLink(testLinkId);

        assertThat(chatIds).hasSize(1);
        assertThat(chatIds.getFirst()).isEqualTo(chat.getId());
    }
}
