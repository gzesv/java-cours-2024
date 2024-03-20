package edu.java.domain.repository;

import edu.java.domain.ChatRepository;
import edu.java.model.Chat;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private static final String FIND_BY_ID = "SELECT * FROM chat WHERE id = ?";
    private static final String ADD_CHAT = "INSERT INTO chat (id) VALUES (?)";
    private static final String REMOVE_CHAT = "DELETE FROM chat WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public Optional<Chat> findById(long chatId) {
        return jdbcTemplate
            .query(FIND_BY_ID, new BeanPropertyRowMapper<>(Chat.class), chatId)
            .stream()
            .findAny();
    }

    public void add(long id) {
        jdbcTemplate.update(ADD_CHAT, id);
    }

    public void remove(long chatId) {
        jdbcTemplate.update(REMOVE_CHAT, chatId);
    }
}
