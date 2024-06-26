package com.ssafy.backend.domain.security.exception;


import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{

    private final AuthErrorCode errorCode;
    public AuthException(AuthErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
