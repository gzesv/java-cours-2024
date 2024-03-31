package edu.java.domain.repository;

import edu.java.model.Link;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.repository.jdbc.JdbcChatToLinkRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationEnvironment;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
}
