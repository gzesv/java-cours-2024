package edu.java.repository.jpa;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.scrapper.IntegrationEnvironment;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaChatRepository chatRepository;

    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void saveTest() {
        Chat chat = new Chat(1L);

        chatRepository.save(chat);

        assertThat(chatRepository.existsById(chat.getId())).isTrue();
    }

    @Test
    public void findByIdEmptyResultTest() {
        boolean actual = chatRepository.existsById(122436435254L);

        assertThat(actual).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() {
        Chat chat = new Chat(1L);
        chatRepository.save(chat);

        chatRepository.delete(chat);

        assertThat(chatRepository.existsById(chat.getId())).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChatsIdsWithLinkTest() {
        Chat chat1 = new Chat(1L);
        Chat chat2 = new Chat(2L);
        chatRepository.save(chat1);
        chatRepository.save(chat2);
        linkRepository.save(new Link("https://github.com"));
        Link link = linkRepository.findByUrl("https://github.com");
        linkRepository.saveChatToLink(chat1.getId(), link.getId());
        linkRepository.saveChatToLink(chat2.getId(), link.getId());

        List<Long> chats = chatRepository.findAllChatsIdsWithLink(link.getId());

        assertThat(chats.size()).isEqualTo(2);
    }
}
