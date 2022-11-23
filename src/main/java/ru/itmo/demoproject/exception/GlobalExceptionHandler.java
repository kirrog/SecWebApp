package ru.itmo.demoproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_NOT_FOUND = "Resource with such search parameter not found.";
    private static final String ERROR_VALIDATE_DATA = "Received incorrect data.";

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<Object> businessModelNotFoundException(WebRequest request, ModelNotFoundException exception) {
        log.error(exception.getMessage());

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ERROR_NOT_FOUND)
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessException(WebRequest request, BusinessException exception) {
        log.error(exception.getMessage());

        var responseBody = ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
