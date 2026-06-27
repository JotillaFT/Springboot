package com.pruebas.demo.service;

import com.pruebas.demo.entity.Post;
import com.pruebas.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostService {
    // Repositorio de Spring Data para acceder a la tabla de posts.
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public List<Post> obtenerPosts(){
        return postRepository.findAll();
    }

    public Post crearPost(Post post){
        // Guarda el post en base de datos. La relacion con Usuario ya debe venir asignada.
        postRepository.save(post);
        return post;
    }

    public Post obtenerPostPorId(int id){

        // En este servicio todavia se devuelve null si no existe.
        // Es otra forma de manejarlo, distinta a la excepcion usada en UsuarioService.
        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()){
            return post.get();
        }

        return null;
    }

    public List<Post> obtenerPostPorUsuario(Integer usuarioId){
        return postRepository.findByUsuarioId(usuarioId);
    }

    public boolean borrarPost(int id){
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Post actualizarPost(int id, Post postActualizado){
        Optional<Post> postOptional = postRepository.findById(id);

        if(postOptional.isPresent()) {

            Post post = postOptional.get();

            post.setTitulo(postActualizado.getTitulo());
            post.setContenido(postActualizado.getContenido());

            return postRepository.save(post);
        }

        return null;
    }

    public long contarPost(){
        return postRepository.count();
    }

    public List<Post> buscarPorTitulo(String titulo){
        return postRepository.findByTitulo(titulo);
    }
}

