package edu.java.model;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public record Link(
    long id,

    String url,

    OffsetDateTime lastUpdateTime,

    OffsetDateTime lastCheckTime
) {
    public Link(String url) {
        this(
            Long.MIN_VALUE,
            url,
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault())
        );
    }
}
