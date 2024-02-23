package com.webapp.globalexception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException ex) {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("error", "Resource Not Found.");
        responseData.put("url", ex.getRequestURL());
        return new ResponseEntity<>(responseData, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
