package com.pruebas.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Ponle un titulo anda")
    private String titulo;
    @NotBlank(message = "Como vas a hacer un post sin contenido tontito")
    private String contenido;
    @NotNull(message = "Digo yo que de alguien sera este post no?")
    @ManyToOne
    @JsonIgnoreProperties({"posts"})
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Post(){}

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
