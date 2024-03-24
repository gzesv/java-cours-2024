package edu.java.repository.jpa;

import edu.java.model.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {

    @Query(nativeQuery = true, value = "SELECT chat_id FROM chat_to_link WHERE link_id = :linkId")
    List<Long> findAllChatsIdsWithLink(long linkId);
}
