package com.pruebas.demo.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    // Clave primaria de la tabla. IDENTITY deja que MySQL genere el valor.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer edad;

    // Relacion 1 usuario -> muchos posts.
    // mappedBy indica que la columna de union se define en Post.usuario.
    @OneToMany(mappedBy = "usuario")
    private List<Post> posts;

    // Constructor vacio obligatorio para que JPA pueda crear objetos al leer de la base de datos.
    public Usuario() {
    }

    public Usuario(Integer id,String nombre,Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
