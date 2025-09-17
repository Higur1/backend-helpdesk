package io.github.higur.helpdesk.resources.exceptions;

import io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ValidationError errors = new ValidationError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Conflict fields",
                "Validation error",
                request.getRequestURI());
        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler({
            io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException.class,
            org.springframework.dao.DataIntegrityViolationException.class
    })
    public ResponseEntity<StandardError> handleDataIntegrityViolation(
            RuntimeException ex, HttpServletRequest request) {

        String message;

        if (ex instanceof io.github.higur.helpdesk.service.exceptions.DataIntegrityViolationException) {
            message = ex.getMessage();
        } else {
            message = "Cannot delete technician with associated tickets";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Data Violation",
                message,
                request.getRequestURI()
        ));
    }

}
