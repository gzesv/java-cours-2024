package edu.java.bot.exceptionhandler;

import edu.java.bot.dto.Response.ApiErrorResponse;
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
}
