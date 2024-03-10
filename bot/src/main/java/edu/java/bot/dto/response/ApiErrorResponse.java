package edu.java.bot.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record ApiErrorResponse(
    @NotNull
    String description,

    @NotNull
    String code,

    @NotNull
    String exceptionName,

    @NotNull
    String exceptionMessage,

    @NotNull
    List<String> stackTrace
) {
}
