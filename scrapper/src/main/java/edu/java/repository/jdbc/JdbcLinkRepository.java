package edu.java.repository.jdbc;

import edu.java.model.Link;
import edu.java.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    private static final String ADD_LINK = "INSERT INTO link (url, updated_at, checked_at) VALUES (?, ?, ?)";

    private static final String LINK_BY_URL = "SELECT * FROM link WHERE url = ?";

    private static final String LINKS_BY_CHAT_ID = "SELECT l.* "
        + "FROM link l JOIN chat_to_link cl ON l.id = cl.link_id "
        + "JOIN chat c ON c.id = cl.chat_id WHERE c.id = ?";

    private static final String DELETE_FROM_LINK = "DELETE FROM link WHERE id = ?";

    private static final String ALL_OUTDATED_LINKS
        = "SELECT * FROM link WHERE EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - checked_at)) >= ?";

    private static final String UPDATE_LINK
        = "UPDATE link SET updated_at = ?, checked_at = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Link add(Link link) {
        jdbcTemplate.update(ADD_LINK, link.getUrl(), link.getUpdateAt(), link.getCheckAt());
        return findLinkByUrl(link.getUrl()).get();
    }

    @Override
    public Optional<Link> findLinkByUrl(String url) {
        return jdbcTemplate
            .query(LINK_BY_URL, new BeanPropertyRowMapper<>(Link.class), url)
            .stream()
            .findAny();
    }

    @Override
    public void remove(long linkId) {
        jdbcTemplate.update(DELETE_FROM_LINK, linkId);
    }

    @Override
    public List<Link> findAll(long chatId) {
        return jdbcTemplate.query(LINKS_BY_CHAT_ID, new BeanPropertyRowMapper<>(Link.class), chatId);
    }

    @Override
    public List<Link> findAllOutdatedLinks(long interval) {
        return jdbcTemplate.query(ALL_OUTDATED_LINKS, new BeanPropertyRowMapper<>(Link.class), interval);
    }

    @Override
    public void setUpdateAndCheckTime(long id, OffsetDateTime lastUpdateTime, OffsetDateTime lastCheckTime) {
        jdbcTemplate.update(UPDATE_LINK, lastUpdateTime, lastCheckTime, id);
    }
}
