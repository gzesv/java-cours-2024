package edu.java.services.scrapper.kafka;

import edu.java.dto.request.LinkUpdateRequest;
import edu.java.kafka.QueueProducer;
import edu.java.services.scrapper.ScrapperService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaScrapperService implements ScrapperService {

    private final QueueProducer queueProducer;

    @Override
    public void send(List<LinkUpdateRequest> requests) {
        requests.forEach(queueProducer::send);
    }
}
