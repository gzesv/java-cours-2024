package edu.java.bot.service.kafka;

import edu.java.bot.dto.request.LinkUpdateRequest;
import edu.java.bot.service.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueConsumer {

    private final UpdateService updateService;

    private final DlqProducer dlqProducer;

    @KafkaListener(
        topics = "${app.kafka.link-updates-topic.name}",
        groupId = "${app.kafka.link-updates-topic.consumer-group-id}"
    )
    public void listen(LinkUpdateRequest request) {
        try {
            updateService.processUpdate(request);
        } catch (Exception exception) {
            dlqProducer.send(request);
        }
    }
}
