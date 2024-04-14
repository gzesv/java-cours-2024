package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Bean
    @NotNull
    Scheduler scheduler,

    @NotNull
    Kafka kafka,

    @NotNull
    String databaseAccessType,

    boolean useQueue
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record Kafka(
        @NotEmpty
        String bootstrapServers,

        @NotEmpty
        String keySerializer,

        @NotEmpty
        String valueSerializer,

        @NotNull
        LinkUpdatesTopic linkUpdatesTopic,

        @NotNull
        DlqTopic dlqTopic

    ) {
        public record LinkUpdatesTopic(
            @NotEmpty
            String name
        ) {
        }

        public record DlqTopic(
            @NotEmpty
            String name,

            @NotEmpty
            String consumerGroupId
        ) {
        }
    }
}
