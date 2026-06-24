package com.pruebas.demo.dto;

public class PostSimpleDTO {
    // Version reducida de un post, util cuando el post aparece dentro de otro DTO.
    private Integer id;
    private String titulo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
