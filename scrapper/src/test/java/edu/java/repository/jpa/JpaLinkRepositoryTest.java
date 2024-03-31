package edu.java.repository.jpa;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.scrapper.IntegrationEnvironment;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaChatRepository chatRepository;

    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAllByChatsIdTest() {
        Chat chat = new Chat(1L);
        chatRepository.save(chat);
        linkRepository.save(new Link("https://github.com/1"));
        linkRepository.save(new Link("https://github.com/2"));
        Link link1 = linkRepository.findByUrl("https://github.com/1");
        Link link2 = linkRepository.findByUrl("https://github.com/2");
        linkRepository.saveChatToLink(chat.getId(), link1.getId());
        linkRepository.saveChatToLink(chat.getId(), link2.getId());

        List<Link> links = linkRepository.findAllByChatsId(chat.getId());

        assertThat(links.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlTest() {
        linkRepository.save(new Link("https://github.com"));
        String url = "https://github.com";

        Link actual = linkRepository.findByUrl(url);

        assertThat(actual.getUrl()).isEqualTo(url);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlLinkNotExistTest() {
        Link actual = linkRepository.findByUrl("https://github.com/2");

        assertThat(actual).isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void saveChatToLinkTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        linkRepository.save(link);
        chatRepository.save(chat);

        linkRepository.saveChatToLink(chat.getId(), link.getId());

        List<Long> list = linkRepository.findAllChatIdsWithLink(link.getId());
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChatToLinkTest() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://github.com", OffsetDateTime.now(), OffsetDateTime.now());
        linkRepository.save(link);
        chatRepository.save(chat);
        linkRepository.saveChatToLink(chat.getId(), link.getId());

        linkRepository.deleteChatToLink(chat.getId(), link.getId());

        List<Long> list = linkRepository.findAllChatIdsWithLink(link.getId());
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllOutdatedLinksTest() {
        linkRepository.save(new Link(1L, "https://github.com/1",
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault()).minusMinutes(40)
        ));
        linkRepository.save(new Link(2L, "https://github.com/2",
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault()).minusMinutes(40)
        ));
        linkRepository.save(new Link(3L, "https://github.com/3",
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault())
        ));
        long interval = 20L;

        List<Link> links = linkRepository.findAllOutdatedLinks(interval);

        assertThat(links.size()).isEqualTo(2);
    }

}
