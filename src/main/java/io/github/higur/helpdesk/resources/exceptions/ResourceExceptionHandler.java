package io.github.higur.helpdesk.resources.exceptions;

import io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Object Not Found",
                ex.getMessage(),
                request.getRequestURI()
        ));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Data Violation",
                ex.getMessage(),
                request.getRequestURI()
        ));
    }
}
