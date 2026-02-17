package com.kaio.picpay_simplificado.infra;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kaio.picpay_simplificado.dtos.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice // treat errors from all RestControllers
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class) // exception handler receives the type of error inside
    public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception) {
        ExceptionDTO threatResponse = new ExceptionDTO("Usuario já cadastrado", "400");
        return ResponseEntity.badRequest().body(threatResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception) {
        ExceptionDTO threatResponse = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(threatResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity threatValidationErrors(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String errorMessage = fieldErrors.stream()
        .map(f -> f.getField() + ": " + f.getDefaultMessage())
        .collect(Collectors.joining(", "));

        ExceptionDTO threatResponse = new ExceptionDTO(errorMessage, "400");

        return ResponseEntity.badRequest().body(threatResponse);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity threatTransactionException(TransactionException exception) {
        ExceptionDTO threatResponse = new ExceptionDTO(exception.getMessage(), "403");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity handleTransactionException(TransactionException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
}
