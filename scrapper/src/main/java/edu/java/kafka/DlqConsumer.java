package edu.java.kafka;

import edu.java.dto.request.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DlqConsumer {

    @KafkaListener(
        topics = "${app.kafka.dlq-topic.name}",
        groupId = "${app.kafka.dlq-topic.consumer-group-id}"
    )
    public void listen(LinkUpdateRequest request) {
        log.error("Недопустимое сообщение об обновлении: %s".formatted(request));
    }
}
