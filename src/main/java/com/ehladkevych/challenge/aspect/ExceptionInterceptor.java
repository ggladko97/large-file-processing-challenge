package com.ehladkevych.challenge.aspect;

import com.ehladkevych.challenge.exception.DataProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class})
    protected ResponseEntity<Object> badRequest(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Please check request params";
        return handleExceptionInternal(ex,
                ResponseEntity.status(BAD_REQUEST)
                        .body(bodyOfResponse),
                new HttpHeaders(),
                BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value
            = {DataProcessingException.class})
    protected ResponseEntity<Object> internal(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Internal Server Error, please check logs";
        return handleExceptionInternal(ex,
                ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(bodyOfResponse),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(value
            = {RuntimeException.class})
    protected ResponseEntity<Object> unrecognized(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Unrecognized Server Error, please check logs";
        return handleExceptionInternal(ex,
                ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(bodyOfResponse),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
}
