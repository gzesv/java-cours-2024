package edu.java.services.scrapper;

import edu.java.dto.request.LinkUpdateRequest;
import java.util.List;

public interface ScrapperService {
    void send(List<LinkUpdateRequest> requests);
}
