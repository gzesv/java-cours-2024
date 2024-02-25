package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionResponse(List<ItemResponse> items) {
    public record ItemResponse(
        @NotNull
        Owner owner,

        @NotNull
        @JsonProperty("question_id")
        Long id,

        @NotNull
        @JsonProperty("view_count")
        Integer viewCount,

        @NotNull
        @JsonProperty("answer_count")
        Integer answerCount,

        @NotNull
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {
    }
}
