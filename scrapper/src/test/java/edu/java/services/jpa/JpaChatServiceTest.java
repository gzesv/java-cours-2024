package edu.java.services.jpa;

import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.model.Chat;
import edu.java.repository.jpa.JpaChatRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaChatServiceTest {
    @Mock
    private JpaChatRepository chatRepository;

    @InjectMocks
    private JpaChatService chatService;

    @Test
    void addChatTest() {
        Chat chat = new Chat(1L);

        chatService.addChat(chat);

        verify(chatRepository).save(chat);
    }

    @Test
    void addChatWhenChatExistsTest() {
        Chat chat = new Chat(1L);
        when(chatRepository.existsById(chat.getId())).thenReturn(true);

        assertThatExceptionOfType(ChatAlreadyExistsException.class)
            .isThrownBy(() -> chatService.addChat(chat));
    }

    @Test
    public void deleteChatTest() {
        Chat chat = new Chat(1L);
        when(chatRepository.existsById(chat.getId())).thenReturn(true);

        chatService.deleteChat(chat);

        verify(chatRepository).delete(chat);
    }

    @Test
    public void deleteChatWhenChatNotExistsTest() {
        Chat chat = new Chat(1L);

        assertThatExceptionOfType(ChatNotFoundException.class)
            .isThrownBy(() -> chatService.deleteChat(chat));
    }

    @Test
    void isChatExistsTest() {
        Chat chat = new Chat(1L);

        chatService.isChatExists(chat.getId());

        verify(chatRepository).existsById(chat.getId());
    }

    @Test
    void findAllChatsIdsWithLinkTest() {
        Chat chat = new Chat(1L);
        long testLinkId = 123;
        when(chatRepository.findAllChatsIdsWithLink(testLinkId)).thenReturn(List.of(chat.getId()));

        List<Long> chatIds = chatService.findAllChatsIdsWithLink(testLinkId);

        assertThat(chatIds).hasSize(1);
        assertThat(chatIds.getFirst()).isEqualTo(chat.getId());
    }
}
