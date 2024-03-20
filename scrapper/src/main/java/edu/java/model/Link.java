package edu.java.model;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private long id;

    private String url;

    private OffsetDateTime updateAt;

    private OffsetDateTime checkAt;

    public Link(String url) {
        this(
            Long.MIN_VALUE,
            url,
            OffsetDateTime.now(ZoneId.systemDefault()),
            OffsetDateTime.now(ZoneId.systemDefault())
        );
    }
}
