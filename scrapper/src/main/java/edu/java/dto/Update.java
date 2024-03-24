package edu.java.dto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record Update(
    @NotNull
    Long linkId,

    @NotNull
    String url,

    @NotNull
    String description,

    @NotNull
    OffsetDateTime updateTime
) {
}
