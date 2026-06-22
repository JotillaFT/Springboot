package com.pruebas.demo.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateUsuarioRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La edad es obligatoria")
    @Min(value=0, message = "Como va a tener edad negativa pipiolo")
    @Max(value=120, message = "Nah demasiado viejo, ese ya esta muerto")
    private Integer edad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
