package edu.java.services.jdbc;

import edu.java.domain.ChatToLinkRepository;
import edu.java.domain.LinkRepository;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import edu.java.model.Chat;
import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class JdbcLinkServiceTest {

    @InjectMocks
    private JdbcLinkService linkService;

    @Mock
    private JdbcChatService chatService;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private ChatToLinkRepository chatToLinkRepository;

    @Test
    void getLinksTest() {
        Chat chat = new Chat(1L);
        Mockito.when(chatService.isChatExists(chat.getId())).thenReturn(true);

        linkService.getLinks(chat.getId());

        Mockito.verify(linkRepository).findAll(chat.getId());
    }

    @Test
    public void getLinksWhenChatNotExistsTest() {
        Chat chat = new Chat(1L);

        Mockito.doThrow(ChatNotFoundException.class)
            .when(chatService).isChatExists(chat.getId());
        assertThatThrownBy(() -> linkService.getLinks(chat.getId()))
            .isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    void addTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        Mockito.when(chatService.isChatExists(chat.getId())).thenReturn(true);
        Mockito.when(linkRepository.findLinkByUrl(link.getUrl())).thenReturn(Optional.empty());
        Mockito.when(linkRepository.add(link)).thenReturn(new Link());

        linkService.add(chat.getId(), link);

        Mockito.verify(linkRepository).add(link);
    }

    @Test
    void addWhenChatNotExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        doThrow(ChatNotFoundException.class)
            .when(chatService).isChatExists(chat.getId());
        assertThatThrownBy(() -> linkService.add(chat.getId(), link))
            .isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    void addWhenLinkExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        Mockito.when(chatService.isChatExists(chat.getId())).thenReturn(true);
        Mockito.when(linkRepository.findLinkByUrl(link.getUrl())).thenReturn(Optional.of(link));

        linkService.add(chat.getId(), link);

        Mockito.verify(chatToLinkRepository).add(chat.getId(), link.getId());
    }

    @Test
    void removeTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        Mockito.when(chatService.isChatExists(chat.getId())).thenReturn(true);
        Mockito.when(linkRepository.findLinkByUrl(link.getUrl())).thenReturn(Optional.of(link));

        linkService.remove(chat.getId(), link);

        Mockito.verify(chatToLinkRepository).remove(chat.getId(), link.getId());
        Mockito.verify(linkRepository).remove(link.getId());
    }

    @Test
    void removeWhenLinkNotExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        Mockito.when(chatService.isChatExists(chat.getId())).thenReturn(true);

        assertThatThrownBy(() -> linkService.remove(chat.getId(), link))
            .isInstanceOf(LinkNotFoundException.class);
    }

    @Test
    void findAllOutdatedLinks() {
        int interval = 60;

        linkService.findAllOutdatedLinks(interval);

        Mockito.verify(linkRepository).findAllOutdatedLinks(interval);
    }

    @Test
    void setUpdateAndCheckTime() {
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());

        linkService.setUpdateAndCheckTime(link.getId(), link.getUpdateAt(), link.getCheckAt());

        Mockito.verify(linkRepository).setUpdateAndCheckTime(link.getId(), link.getUpdateAt(), link.getCheckAt());
    }
}
