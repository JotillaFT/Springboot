package com.pruebas.demo.exception;

import com.pruebas.demo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex){
        Map<String,String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        });
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarUsuarioNoEncontrado(UsuarioNotFoundException ex){
        LocalDateTime fecha = LocalDateTime.now();
        Integer status = HttpStatus.NOT_FOUND.value();
        String mensaje = ex.getMessage();
        ErrorResponse error = new ErrorResponse(fecha, status, mensaje);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
