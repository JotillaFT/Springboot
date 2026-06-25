package com.pruebas.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePostRequest {
    // DTO de entrada para crear posts. Es lo que el cliente envia a POST /posts.

    @NotBlank(message = "Es necesario poner un Titulo")
    private String titulo;

    @NotBlank(message = "Es necesario poner contenido")
    private String contenido;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
