package com.resource.api.exception;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        System.out.println("validationErrors" + validationErrors);
        return getExceptionResponseEntity(request, validationErrors);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception, WebRequest request) {
        List<String> validationErrors = exception.getConstraintViolations().stream().
                map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
        System.out.println("validationErrors" + validationErrors);
        return getExceptionResponseEntity(request, validationErrors);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(WebRequest request, List<String> errors) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String errorsMessage = !errors.isEmpty() ? errors.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")) : HttpStatus.BAD_REQUEST.getReasonPhrase();

        final String path = request.getDescription(false);
        body.put("TIMESTAMP", Instant.now());
        body.put("STATUS", HttpStatus.BAD_REQUEST.value());
        body.put("ERRORS", errorsMessage);
        body.put("PATH", path);
        body.put("MESSAGE", HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}