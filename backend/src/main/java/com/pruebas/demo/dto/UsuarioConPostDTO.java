package com.pruebas.demo.dto;

import java.util.List;

public class UsuarioConPostDTO {
    private Integer id;
    private String nombre;
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
