package com.pruebas.demo.repository;

import com.pruebas.demo.entity.Post;
import com.pruebas.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Repositorio de Post. Al extender JpaRepository ya tienes findAll, findById, save, deleteById, etc.
public interface PostRepository extends JpaRepository<Post, Integer>{
    // Metodos de consulta derivados del nombre del metodo.
    List<Post> findByTitulo(String titulo);
    List<Post> findByContenido(String contenido);
    List<Post> findByUsuario(Usuario usuario);
}
