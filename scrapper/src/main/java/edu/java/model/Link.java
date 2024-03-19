package edu.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.time.ZoneId;

//@AllArgsConstructor
//public record Link(
//    long id,
//
//    String url,
//
//    OffsetDateTime lastUpdateTime,
//
//    OffsetDateTime lastCheckTime
//) {
//    public Link(String url) {
//        this(
//            Long.MIN_VALUE,
//            url,
//            OffsetDateTime.now(ZoneId.systemDefault()),
//            OffsetDateTime.now(ZoneId.systemDefault())
//        );
//    }
//}

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
