package com.pruebas.demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    // Formato comun para respuestas de error de la API.
    private LocalDateTime timestamp;
    private Integer status;

    // Mensaje simple para errores generales, por ejemplo "Usuario no encontrado".
    private String error;

    // Mapa de errores de validacion: nombre del campo -> mensaje.
    private Map<String,String> errors;

    // Constructor para errores simples.
    public ErrorResponse(LocalDateTime timestamp, Integer status, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }

    // Constructor para errores de validacion con varios campos.
    public ErrorResponse(LocalDateTime timestamp, Integer status, Map<String, String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
