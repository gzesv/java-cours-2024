package edu.java.services.scheduler;

import edu.java.client.bot.BotClient;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.services.scrapper.ScrapperService;
import edu.java.services.updater.LinkUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private static final int INTERVAL = 30;

    private final ScrapperService scrapperService;

    private final LinkUpdateService linkUpdateService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        List<LinkUpdateRequest> updates = linkUpdateService.fetchAllUpdates(INTERVAL);
        scrapperService.send(updates);
    }
}
