package edu.java.configuration;

import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.services.ChatService;
import edu.java.services.LinkService;
import edu.java.services.jpa.JpaChatService;
import edu.java.services.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService jpaLinkService(
        JpaChatService jpaChatService,
        JpaLinkRepository jpaLinkRepository
    ) {
        return new JpaLinkService(jpaChatService, jpaLinkRepository);
    }

    @Bean
    public ChatService chatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }

    @Bean
    public JpaChatService jpaChatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }
}
