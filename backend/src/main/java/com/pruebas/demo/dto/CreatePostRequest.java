package com.pruebas.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePostRequest {
    // DTO de entrada para crear o editar posts.
    // El usuario propietario no va aqui: al crear se obtiene desde /usuarios/{id}/posts.

    // Titulo visible del post.
    @NotBlank(message = "Es necesario poner un Titulo")
    private String titulo;

    // Cuerpo completo del post.
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
