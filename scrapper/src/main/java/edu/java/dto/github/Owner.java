package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record Owner(
    @NotNull
    @JsonProperty("login")
    String login,

    @NotNull
    @JsonProperty("id")
    Long id
) {
}
