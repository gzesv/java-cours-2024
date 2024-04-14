package edu.java.configuration;

import edu.java.client.bot.BotClient;
import edu.java.kafka.QueueProducer;
import edu.java.services.scrapper.ScrapperService;
import edu.java.services.scrapper.http.HttpScrapperService;
import edu.java.services.scrapper.kafka.KafkaScrapperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperServiceConfiguration {
    @Bean
    public ScrapperService scrapperService(
        ApplicationConfig config,
        BotClient botClient,
        QueueProducer queueProducer
    ) {
        if (config.useQueue()) {
            return new KafkaScrapperService(queueProducer);
        }
        return new HttpScrapperService(botClient);
    }
}
