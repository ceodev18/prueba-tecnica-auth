package com.api.auth.model.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    public ErrorResponse(String message){
        this.message = message;
    }
}
