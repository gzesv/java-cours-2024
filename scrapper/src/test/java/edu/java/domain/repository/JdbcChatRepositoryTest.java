package edu.java.domain.repository;

import edu.java.model.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationEnvironment;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Autowired
    private JdbcClient client;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        chatRepository.add(1L);

        assertThat(client.sql("SELECT * FROM chat WHERE id=1").query(Long.class).list()).isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback
    public void findByIdTest() {
        long id = 7L;
        chatRepository.add(id);

        Optional<Chat> chat = chatRepository.findById(id);

        assertThat(chat).isPresent();
    }

    @Test
    @Transactional
    @Rollback
    public void findByIdOptionalIsEmptyTest() {
        long id = 7L;

        Optional<Chat> chat = chatRepository.findById(id);

        assertThat(chat).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        long chatId = 1L;
        client.sql("INSERT INTO chat(id) values(?)").param(chatId).update();

        chatRepository.remove(chatId);

        assertThat(client.sql("SELECT * FROM chat").query(Long.class).list()).isEmpty();
    }
}
