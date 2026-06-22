package com.pruebas.demo.service;

import com.pruebas.demo.entity.Post;
import com.pruebas.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public List<Post> obtenerPosts(){
        return postRepository.findAll();
    }

    public Post crearPost(Post post){
        postRepository.save(post);
        return post;
    }

    public Post obtenerPostPorId(int id){

        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent()){
            return post.get();
        }

        return null;
    }

    public String borrarPost(int id){
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return "Post eliminado";
        }
        return "Post no encontrado";
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

