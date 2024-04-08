package edu.java.dto.response;

import jakarta.validation.constraints.NotEmpty;

public record LinkUpdateResponse(
    @NotEmpty
    String message
) {
}
