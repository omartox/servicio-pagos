package com.prueba.pagos.exceptions;


import com.prueba.pagos.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ServiceErrorException.class)
    public ResponseEntity<ErrorResponse> handleServiceErrorException(ServiceErrorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
}
