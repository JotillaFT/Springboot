package com.pruebas.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePostRequest {
    // DTO de entrada para crear posts. Es lo que el cliente envia a POST /posts.

    @NotBlank(message = "Es necesario poner un Titulo")
    private String titulo;

    @NotBlank(message = "Es necesario poner contenido")
    private String contenido;

    // No se envia el objeto Usuario entero: basta con su id para buscarlo en backend.
    @NotNull(message = "Debes indicar el usuario")
    private Integer usuarioId;

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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
