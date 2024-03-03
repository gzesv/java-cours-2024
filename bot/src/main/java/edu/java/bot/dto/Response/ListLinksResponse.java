package edu.java.bot.dto.Response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ListLinksResponse(
    @NotNull
    List<LinkResponse> links,

    int size
) {
}
