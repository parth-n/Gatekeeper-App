package com.example.Gatekeeper_backend.utils;

import com.example.Gatekeeper_backend.Exceptions.BadRequest;
import com.example.Gatekeeper_backend.Exceptions.NotFound;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionalHandler {

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<String> handleBadRequestException(final BadRequest badRequestexception){
        return ResponseEntity.badRequest().body(badRequestexception.getMessage()) ;
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<String> handleNotFoundException (final NotFound notFoundexception){
        return ResponseEntity.notFound().build() ;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException (final RuntimeException runtimeException){
        return ResponseEntity.internalServerError().body(runtimeException.getMessage()) ;
    }

}
