package edu.java.controller;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.mapper.DefaultObjectMapper;
import edu.java.model.Link;
import edu.java.services.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    private final DefaultObjectMapper mapper;

    @Operation(
        summary = "Получить все отслеживаемые ссылки",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ссылки успешно получены",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ListLinksResponse.class)
                )
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
    @GetMapping
    public ResponseEntity<ListLinksResponse> getLinks(
        @RequestHeader("Tg-Chat-Id") Long chatId
    ) {
        List<Link> links = linkService.getLinks(chatId);
        return new ResponseEntity<>(mapper.mapToListLinksResponse(links), HttpStatus.OK);
    }

    @Operation(
        summary = "Добавить отслеживание ссылки",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ссылка успешно добавлена",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LinkResponse.class)
                )
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
    @PostMapping
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        Link link = linkService.add(chatId, mapper.convertToLink(addLinkRequest));
        return new ResponseEntity<>(mapper.linkToLinkResponse(link), HttpStatus.OK);
    }

    @Operation(
        summary = "Убрать отслеживание ссылки",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ссылка успешно убрана",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LinkResponse.class)
                )
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
                description = "Ссылка не найдена",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiErrorResponse.class)
                )
            )
        }
    )
    @DeleteMapping
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        Link link = linkService.remove(chatId, mapper.convertToLink(removeLinkRequest));
        return new ResponseEntity<>(mapper.linkToLinkResponse(link), HttpStatus.OK);
    }
}
