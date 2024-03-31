package edu.java.services.jpa;

import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repository.jpa.JpaLinkRepository;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaLinkServiceTest {
    @Mock
    private JpaChatService chatService;

    @Mock
    private JpaLinkRepository linkRepository;

    @InjectMocks
    private JpaLinkService linkService;

    @Test
    void getLinksTest() {
        Link link = new Link("https://github.com/1");
        Chat chat = new Chat(1L);
        when(chatService.isChatNotExists(chat.getId())).thenReturn(false);
        when(linkRepository.findAllByChatsId(chat.getId())).thenReturn(List.of(link));

        List<Link> links = linkService.getLinks(chat.getId());

        verify(linkRepository).findAllByChatsId(chat.getId());
        assertThat(links.size()).isEqualTo(1);
        assertThat(links.getFirst()).isEqualTo(link);
    }

    @Test
    public void getLinksWhenChatNotExistsTest() {
        Chat chat = new Chat(1L);
        doThrow(ChatNotFoundException.class)
            .when(chatService).isChatNotExists(chat.getId());

        assertThatThrownBy(() -> linkService.getLinks(chat.getId()))
            .isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    void addTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        when(chatService.isChatNotExists(chat.getId())).thenReturn(false);
        when(linkRepository.findByUrl(link.getUrl())).thenReturn(null);
        when(linkRepository.saveAndFlush(link)).thenReturn(link);

        Link actual = linkService.add(chat.getId(), link);

        verify(linkRepository).saveAndFlush(link);
        verify(linkRepository).saveChatToLink(chat.getId(), link.getId());
        assertThat(actual).isEqualTo(link);
    }

    @Test
    void addWhenChatNotExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        doThrow(ChatNotFoundException.class)
            .when(chatService).isChatNotExists(chat.getId());

        assertThatThrownBy(() -> linkService.add(chat.getId(), link))
            .isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    void addWhenLinkExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        when(chatService.isChatNotExists(chat.getId())).thenReturn(false);
        when(linkRepository.findByUrl(link.getUrl())).thenReturn(link);

        Link actual = linkService.add(chat.getId(), link);

        verify(linkRepository).saveChatToLink(chat.getId(), link.getId());
        assertThat(actual).isEqualTo(link);
    }

    @Test
    void removeTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        when(chatService.isChatNotExists(chat.getId())).thenReturn(false);
        when(linkRepository.findByUrl(link.getUrl())).thenReturn(link);

        Link actual = linkService.remove(chat.getId(), link);

        verify(linkRepository).deleteChatToLink(chat.getId(), link.getId());
        verify(linkRepository).delete(link);
        assertThat(actual).isEqualTo(link);
    }

    @Test
    void removeWhenLinkNotExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        when(chatService.isChatNotExists(chat.getId())).thenReturn(false);

        assertThatThrownBy(() -> linkService.remove(chat.getId(), link))
            .isInstanceOf(LinkNotFoundException.class);
    }

    @Test
    void findAllOutdatedLinks() {
        var list = List.of(
            new Link(1L, "https://github.com/1",
                OffsetDateTime.now(ZoneId.systemDefault()),
                OffsetDateTime.now(ZoneId.systemDefault()).minusMinutes(400)
            ),
            new Link(2L, "https://github.com/2",
                OffsetDateTime.now(ZoneId.systemDefault()),
                OffsetDateTime.now(ZoneId.systemDefault()).minusMinutes(400)
            )
        );
        int interval = 60;
        when(linkRepository.findAllOutdatedLinks(interval)).thenReturn(list);

        List<Link> actual = linkService.findAllOutdatedLinks(interval);

        verify(linkRepository).findAllOutdatedLinks(interval);
        assertThat(actual).isEqualTo(list);
    }

    @Test
    void setUpdateAndCheckTime() {
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        when(linkRepository.findById(link.getId())).thenReturn(Optional.of(link));

        linkService.setUpdateAndCheckTime(link.getId(), link.getUpdateAt(), link.getCheckAt());

        verify(linkRepository).save(link);
    }

    @Test
    void setUpdateAndCheckTimeWhenLinkNotExistsTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        doThrow(LinkNotFoundException.class).when(linkRepository).findById(chat.getId());

        assertThatThrownBy(() -> linkService.setUpdateAndCheckTime(chat.getId(), link.getUpdateAt(), link.getCheckAt()))
            .isInstanceOf(LinkNotFoundException.class);
    }
}
