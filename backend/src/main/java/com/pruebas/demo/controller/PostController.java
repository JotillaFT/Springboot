package com.pruebas.demo.controller;

import com.pruebas.demo.dto.CreatePostRequest;
import com.pruebas.demo.dto.PostResponseDTO;
import com.pruebas.demo.entity.Post;
import com.pruebas.demo.entity.Usuario;
import com.pruebas.demo.service.PostService;
import com.pruebas.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class PostController {
    // Servicio principal para trabajar con posts.
    private final PostService postService;

    // Se usa para buscar el usuario propietario antes de crear el post.
    private final UsuarioService usuarioService;

    // Conversion manual de entidad Post a DTO.
    // En UsuarioController usas MapStruct; aqui esta hecho a mano para comparar ambos enfoques.
    private PostResponseDTO toDTO(Post post){
        PostResponseDTO dto = new PostResponseDTO();

        dto.setId(post.getId());
        dto.setTitulo(post.getTitulo());
        dto.setContenido(post.getContenido());
        dto.setNombreUsuario(post.getUsuario().getNombre());

        return dto;
    }

    public PostController(PostService postService, UsuarioService usuarioService){
        this.postService = postService;
        this.usuarioService = usuarioService;
    }

    // POST /posts
    // Crea un post asociado a un usuario existente.
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> crearPost(@Valid @RequestBody CreatePostRequest request){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(request.getUsuarioId());

        if (usuario == null){
            return ResponseEntity.notFound().build();
        }

        Post nuevoPost =  new Post();

        nuevoPost.setTitulo(request.getTitulo());
        nuevoPost.setContenido(request.getContenido());
        nuevoPost.setUsuario(usuario);

        Post postGuardado = postService.crearPost(nuevoPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(postGuardado));
    }

    // GET /posts
    // Lista todos los posts y los devuelve en formato DTO.
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> obtenerPosts(){
        List<PostResponseDTO> responses = new ArrayList<>();

        List<Post> posts =  postService.obtenerPosts();

        for (Post post : posts){
            responses.add(toDTO(post));
        }

        return ResponseEntity.ok(responses);
    }
}
