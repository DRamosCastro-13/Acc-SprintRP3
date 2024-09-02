package com.example.taskservice.handlers;

import org.springframework.validation.BindingResult;

public class InvalidTaskException extends RuntimeException{

    private final BindingResult bindingResult;

    public InvalidTaskException(BindingResult bindingResult) {
        super("Validation errors occurred");
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
