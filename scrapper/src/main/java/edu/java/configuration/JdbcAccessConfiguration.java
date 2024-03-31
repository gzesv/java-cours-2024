package edu.java.configuration;

import edu.java.repository.ChatRepository;
import edu.java.repository.ChatToLinkRepository;
import edu.java.repository.LinkRepository;
import edu.java.services.ChatService;
import edu.java.services.LinkService;
import edu.java.services.jdbc.JdbcChatService;
import edu.java.services.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
        LinkRepository linkRepository,
        ChatToLinkRepository chatToLinkRepository,
        JdbcChatService jdbcChatService
    ) {
        return new JdbcLinkService(linkRepository, chatToLinkRepository, jdbcChatService);
    }

    @Bean
    public ChatService chatService(
        ChatRepository chatRepository,
        ChatToLinkRepository chatToLinkRepository
    ) {
        return new JdbcChatService(chatRepository, chatToLinkRepository);
    }

    @Bean
    public JdbcChatService jdbcChatService(
        ChatRepository chatRepository,
        ChatToLinkRepository chatToLinkRepository
    ) {
        return new JdbcChatService(chatRepository, chatToLinkRepository);
    }
}
