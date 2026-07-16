package com.examen.spring.app.springboot_examen.exceptions;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.stream.Collectors;

// Manejador global para interceptar excepciones y dar respuestas limpias en JSON
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de validación de los campos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.badRequest().body(errors);
    }

    // Maneja el error cuando no se encuentra un registro
    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(java.util.NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Recurso no encontrado"));
    }

    // Captura cualquier otro error no controlado para evitar stacktraces técnicos
    // en el cliente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of("error", "Ocurrió un error interno, intente más tarde"));
    }
}
