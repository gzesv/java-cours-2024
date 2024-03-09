package edu.java.exceptionhandler;

import edu.java.dto.response.ApiErrorResponse;
import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkAlreadyExistsException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> notValidMethodArgumentException(MethodArgumentNotValidException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Неверный параметр запроса")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getDetailMessageCode())
                .stackTrace(Arrays.stream(exception.getStackTrace())
                    .map(StackTraceElement::toString)
                    .toList())
                .build());
    }

    @ExceptionHandler(ChatAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyExistsException(ChatAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Чат уже существует")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stackTrace(Arrays.stream(exception.getStackTrace())
                    .map(StackTraceElement::toString)
                    .toList())
                .build());
    }

    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyExistsException(LinkAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Ссылка уже добавлена")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stackTrace(Arrays.stream(exception.getStackTrace())
                    .map(StackTraceElement::toString)
                    .toList())
                .build());
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> chatNotFoundException(ChatNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.builder()
                .description("Чат не найден")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .stackTrace(Arrays.stream(exception.getStackTrace())
                    .map(StackTraceElement::toString)
                    .toList())
                .build());
    }
}
