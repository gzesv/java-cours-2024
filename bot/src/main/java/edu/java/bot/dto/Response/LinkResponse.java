package edu.java.bot.dto.Response;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkResponse(
    Long id,

    @NotNull
    URI url
) {
}
