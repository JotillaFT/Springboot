package com.pruebas.demo.repository;

import com.pruebas.demo.entity.Post;
import com.pruebas.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findByTitulo(String titulo);
    List<Post> findByContenido(String contenido);
    List<Post> findByUsuario(Usuario usuario);
}
