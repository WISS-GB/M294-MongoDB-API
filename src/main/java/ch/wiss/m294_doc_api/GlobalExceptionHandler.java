package ch.wiss.m294_doc_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("Invalid JSON format: " + ex.getMessage() + ", did you wrap your attributes in the content key?", HttpStatus.BAD_REQUEST);
    }

    // Other exception handlers...
}

