package com.example.taskservice.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({TaskNotFoundException.class})
    public Mono<ResponseEntity<String>> handlerTaskNotFoundEx ( TaskNotFoundException ex ){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler({InvalidTaskException.class})
    public Mono<ResponseEntity<ValidationErrorResponse>> handleInvalidUserException(InvalidTaskException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        for (Object error : bindingResult.getAllErrors()) {
            errorMessages.add(((FieldError) error).getField() + ": " + ((FieldError) error).getDefaultMessage());
        }

        ValidationErrorResponse response = new ValidationErrorResponse(errorMessages.toString());
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }
}

