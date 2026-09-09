package com.prueba.pagos.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceErrorException extends RuntimeException{

    private final HttpStatus status;

    public ServiceErrorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
