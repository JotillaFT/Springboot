package com.pruebas.demo.controller;

import com.pruebas.demo.dto.CreatePostRequest;
import com.pruebas.demo.dto.PostResponseDTO;
import com.pruebas.demo.entity.Post;
import com.pruebas.demo.entity.Usuario;
import com.pruebas.demo.mapper.PostMapper;
import com.pruebas.demo.service.PostService;
import com.pruebas.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
public class PostController {
    // Servicio principal para trabajar con posts.
    private final PostService postService;

    // Se usa para buscar el usuario propietario antes de crear el post.
    private final UsuarioService usuarioService;

    private final PostMapper postMapper;

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

    public PostController(PostService postService, PostMapper postMapper, UsuarioService usuarioService){
        this.postService = postService;
        this.postMapper = postMapper;
        this.usuarioService = usuarioService;
    }

    // POST /posts
    // Crea un post asociado a un usuario existente.
    @PostMapping("usuarios/{id}/posts")
    public ResponseEntity<PostResponseDTO> crearPost(@PathVariable Integer id,@Valid @RequestBody CreatePostRequest request){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

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
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> obtenerPosts(){
        List<Post> posts = postService.obtenerPosts();
        return ResponseEntity.ok(postMapper.toDtoList(posts));
    }


    // Ver un post concreto
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> obtenerPostId(@PathVariable Integer id){
        Post post = postService.obtenerPostPorId(id);
        if(post==null){
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(postMapper.toDto(post));
    }
}
