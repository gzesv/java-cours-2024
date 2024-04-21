package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,

    @NotNull
    Kafka kafka,

    @NotNull
    Metrics metrics

) {
    public record Kafka(
        @NotEmpty
        String bootstrapServers,

        @NotEmpty
        String keyDeserializer,

        @NotEmpty
        String valueDeserializer,

        @NotNull
        LinkUpdatesTopic linkUpdatesTopic,

        @NotNull
        DlqTopic dlqTopic
    ) {
        public record LinkUpdatesTopic(
            @NotEmpty
            String name,
            @NotEmpty
            String consumerGroupId
        ) {
        }

        public record DlqTopic(
            @NotEmpty
            String name
        ) {
        }
    }

    public record Metrics(ProcessedUpdates processedUpdates) {
        public record ProcessedUpdates(@NotEmpty String name, @NotEmpty String description) { }
    }
}
