package edu.java.bot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LinkRepository {
    private final Map<Long, List<String>> links;

    public LinkRepository() {
        this.links = new HashMap<>();
    }

    public void addLink(Long id, String link) {
        if (!this.links.containsKey(id)) {
            this.links.put(id, new LinkedList<>());
        }
        this.links.get(id).add(link);
    }

    public List<String> getLinks(Long id) {
        if (links.containsKey(id)) {
            return this.links.get(id);
        }
        return new ArrayList<>();
    }

    public void removeLink(Long id, String link) {
        if (links.containsKey(id)) {
            this.links.get(id).remove(link);
        }
    }
}
