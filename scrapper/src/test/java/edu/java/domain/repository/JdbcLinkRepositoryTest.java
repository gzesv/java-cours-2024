package edu.java.domain.repository;

import edu.java.model.Link;
import edu.java.scrapper.IntegrationEnvironment;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcLinkRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Autowired
    private JdbcChatRepository chatRepository;

    @Autowired
    private JdbcChatToLinkRepository chatToLinkRepository;

    @Autowired
    private JdbcClient client;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        String url = "https://github.com";
        linkRepository.add(new Link(url));

        assertThat(client.sql("SELECT * FROM link WHERE url=?")
            .param(url)
            .query(Link.class)
            .list())
            .isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void findLinkByUrlTest() {
        String url = "https://github.com";
        linkRepository.add(new Link(url));
        Optional<Link> link = linkRepository.findLinkByUrl(url);

        linkRepository.remove(link.get().getId());

        assertThat(client.sql("SELECT * FROM link WHERE url=?")
            .param(url)
            .query(Link.class)
            .list())
            .isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        String url = "https://github.com";
        linkRepository.add(new Link(url));

        Optional<Link> link = linkRepository.findLinkByUrl(url);

        assertThat(link.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        long chatId = 1L;
        String url1 = "https://github.com/1";
        String url2 = "https://github.com/2";
        linkRepository.add(new Link(url1));
        linkRepository.add(new Link(url2));
        Link link1 = linkRepository.findLinkByUrl(url1).get();
        Link link2 = linkRepository.findLinkByUrl(url2).get();
        chatRepository.add(chatId);
        chatToLinkRepository.add(chatId, link1.getId());
        chatToLinkRepository.add(chatId, link2.getId());

        List<Link> links = linkRepository.findAll(chatId);

        assertThat(links.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    void findAllOutdatedLinks() {
        linkRepository.add(new Link(1L, "https://github.com/1",
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault()).minusMinutes(40)
        ));
        linkRepository.add(new Link(2L, "https://github.com/2",
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault()).minusMinutes(40)
        ));
        linkRepository.add(new Link(3L, "https://github.com/3",
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault())
        ));

        List<Link> links = linkRepository.findAllOutdatedLinks(30L);

        assertThat(links.size()).isEqualTo(2);
    }
}
