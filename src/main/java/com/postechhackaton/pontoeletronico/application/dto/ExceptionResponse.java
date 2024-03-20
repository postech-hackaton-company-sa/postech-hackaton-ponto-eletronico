package com.postechhackaton.pontoeletronico.application.dto;

import com.postechhackaton.pontoeletronico.application.handler.ApiExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
@AllArgsConstructor
public class ExceptionResponse {
    private final ErrorType errorType;
    private String errorMessage;

    public enum ErrorType {
        RESOURCE_NOT_FOUND, PROCESS_FAILURE, VALIDATION_FAILURE, GENERIC_SERVER_ERROR
    }
}

