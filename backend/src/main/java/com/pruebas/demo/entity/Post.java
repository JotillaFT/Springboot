package com.pruebas.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "post")
public class Post {
    // Clave primaria autogenerada en la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Validaciones que se ejecutan cuando el controller recibe @Valid.
    @NotBlank(message = "Ponle un titulo anda")
    private String titulo;

    @NotBlank(message = "Como vas a hacer un post sin contenido tontito")
    private String contenido;

    // Muchos posts pueden pertenecer al mismo usuario.
    @NotNull(message = "Digo yo que de alguien sera este post no?")
    @ManyToOne
    // Evita que al convertir a JSON se serialicen usuario -> posts -> usuario infinitamente.
    @JsonIgnoreProperties({"posts"})
    // Nombre de la columna FK en la tabla post.
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructor vacio necesario para JPA.
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
