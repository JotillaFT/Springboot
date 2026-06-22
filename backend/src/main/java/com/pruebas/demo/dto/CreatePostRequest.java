package com.pruebas.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePostRequest {
    @NotBlank(message = "Es necesario poner un Titulo")
    private String titulo;

    @NotBlank(message = "Es necesario poner contenido")
    private String contenido;

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
