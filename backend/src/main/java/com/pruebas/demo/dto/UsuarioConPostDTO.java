package com.pruebas.demo.dto;

import java.util.List;

public class UsuarioConPostDTO {
    // DTO de salida para la pantalla o endpoint donde interesa ver el usuario con sus posts.
    private Integer id;
    private String nombre;

    // Se usa PostSimpleDTO para no enviar todo el contenido de cada post.
    private List<PostSimpleDTO> posts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PostSimpleDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostSimpleDTO> posts) {
        this.posts = posts;
    }
}
