package edu.java.domain.repository;

import edu.java.domain.ChatToLinkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcChatToLinkRepository implements ChatToLinkRepository {

    private static final String INSERT_INTO_CHAT_TO_LINK
        = "INSERT INTO chat_to_link (chat_id, link_id) VALUES (?, ?)";

    private static final String DELETE_FROM_CHAT_TO_LINK
        = "DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?";

    private static final String LINK_TRACKERS = "SELECT chat_id FROM chat_link WHERE link_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(long chatId, long linkId) {
        jdbcTemplate.update(INSERT_INTO_CHAT_TO_LINK, chatId, linkId);
    }

    @Override
    public void remove(long chatId, long linkId) {
        jdbcTemplate.update(DELETE_FROM_CHAT_TO_LINK, chatId, linkId);
    }

    @Override
    public List<Long> linkTrackers(long linkId) {
        return jdbcTemplate.queryForList(LINK_TRACKERS, Long.class, linkId);
    }
}