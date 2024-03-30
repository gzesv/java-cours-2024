package edu.java.controller;

import edu.java.dto.response.ApiErrorResponse;
import edu.java.mapper.DefaultObjectMapper;
import edu.java.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final DefaultObjectMapper mapper;

    @Operation(
        summary = "Зарегистрировать чат",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Чат зарегистрирован"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Некорректные параметры запроса",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class)
                )
            )
        }
    )
    @PostMapping("/{id}")
    public void addChat(@PathVariable("id") Long id) {
        chatService.addChat(mapper.convertToChat(id));
    }

    @Operation(
        summary = "Удалить чат",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Чат успешно удален"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Некорректные параметры запроса",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Чат не существует",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class)
                )
            )
        }
    )
    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable("id") Long id) {
        chatService.deleteChat(mapper.convertToChat(id));
    }
}
