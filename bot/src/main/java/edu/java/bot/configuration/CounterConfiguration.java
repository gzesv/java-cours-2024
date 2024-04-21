package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfiguration {
    @Bean
    public Counter processedUpdatesCounter(ApplicationConfig config, MeterRegistry registry) {
        return Counter.builder(config.metrics().processedUpdates().name())
            .description(config.metrics().processedUpdates().description())
            .register(registry);
    }
}
