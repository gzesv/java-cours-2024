package edu.java.dto.Response;

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
