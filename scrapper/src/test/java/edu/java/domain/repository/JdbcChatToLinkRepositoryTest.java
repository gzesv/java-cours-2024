package edu.java.domain.repository;

import edu.java.model.Link;
import edu.java.scrapper.IntegrationEnvironment;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcChatToLinkRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatToLinkRepository chatToLinkRepository;

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Autowired
    private JdbcChatRepository chatRepository;

    @Autowired
    private JdbcClient client;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        long chatId = 1L;
        long linkId = 1L;
        chatRepository.add(chatId);
        linkRepository.add(new Link(linkId, "https://github.com/1",
            OffsetDateTime.now(ZoneId.systemDefault()), OffsetDateTime.now(ZoneId.systemDefault())
        ));

        chatToLinkRepository.add(chatId, linkId);

        assertThat(client.sql("SELECT chat_id FROM chat_to_link WHERE chat_id=? AND link_id=?")
            .params(chatId, linkId)
            .query(Long.class)
            .list())
            .isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void remove() {
        long chatId = 1L;
        long linkId = 1L;
        chatRepository.add(chatId);

        linkRepository.add(new Link(linkId, "https://github.com/1",
            OffsetDateTime.now(ZoneId.systemDefault()), OffsetDateTime.now(ZoneId.systemDefault())
        ));

        chatToLinkRepository.remove(chatId, linkId);

        assertThat(client.sql("SELECT link_id FROM chat_to_link WHERE link_id=?")
            .param(linkId)
            .query(Long.class)
            .list())
            .isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void findAllChatIdsWithLink() {
        long chatId1 = 1L;
        long chatId2 = 2L;
        long chatId3 = 3L;
        long linkId = 1L;
        chatRepository.add(chatId1);
        chatRepository.add(chatId2);
        chatRepository.add(chatId3);
        linkRepository.add(new Link(linkId, "https://github.com/1",
            OffsetDateTime.now(ZoneId.systemDefault()), OffsetDateTime.now(ZoneId.systemDefault())
        ));
        chatToLinkRepository.add(chatId1, linkId);
        chatToLinkRepository.add(chatId2, linkId);
        chatToLinkRepository.add(chatId3, linkId);

        List<Long> ids = chatToLinkRepository.findAllChatIdsWithLink(linkId);

        assertThat(ids.size()).isEqualTo(3);
    }
}
