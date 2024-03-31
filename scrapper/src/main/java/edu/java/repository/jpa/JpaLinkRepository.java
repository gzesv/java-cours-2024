package edu.java.repository.jpa;

import edu.java.model.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByChatsId(long chatId);

    Link findByUrl(String url);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO chat_to_link (chat_id, link_id) VALUES (:chatId, :linkId)")
    void saveChatToLink(long chatId, long linkId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM chat_to_link WHERE chat_id = :chatId AND link_id = :linkId")
    void deleteChatToLink(long chatId, long linkId);

    @Query(nativeQuery = true,
           value = "SELECT * FROM link WHERE EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - checked_at)) >= :interval")
    List<Link> findAllOutdatedLinks(long interval);

    @Query(nativeQuery = true, value = "SELECT chat_id FROM chat_to_link WHERE link_id = :linkId")
    List<Long> findAllChatIdsWithLink(long linkId);
}
