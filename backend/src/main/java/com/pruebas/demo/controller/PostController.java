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

import java.util.List;
@RestController
public class PostController {
    // Servicio principal para trabajar con posts.
    private final PostService postService;

    // Se usa para buscar el usuario propietario antes de crear el post.
    private final UsuarioService usuarioService;

    private final PostMapper postMapper;

    // Conversion manual antigua de entidad Post a DTO.
    // Queda comentada como referencia, pero ahora se usa PostMapper para centralizar
    // la conversion y no repetir este codigo en cada endpoint.
//    private PostResponseDTO toDTO(Post post){
//        PostResponseDTO dto = new PostResponseDTO();
//
//        dto.setId(post.getId());
//        dto.setTitulo(post.getTitulo());
//        dto.setContenido(post.getContenido());
//        dto.setNombreUsuario(post.getUsuario().getNombre());
//
//        return dto;
//    }

    public PostController(PostService postService, PostMapper postMapper, UsuarioService usuarioService){
        this.postService = postService;
        this.postMapper = postMapper;
        this.usuarioService = usuarioService;
    }

    // POST /usuarios/{id}/posts
    // Crea un post asociado al usuario indicado en la URL.
    // El body solo trae titulo y contenido; el usuario propietario se busca aparte
    // para guardar la relacion ManyToOne correctamente.
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

        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDto(postGuardado));
    }

    // GET /posts
    // Devuelve todos los posts usando DTOs para no enviar la entidad Usuario completa.
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> obtenerPosts(){
        List<Post> posts = postService.obtenerPosts();
        return ResponseEntity.ok(postMapper.toDtoList(posts));
    }


    // GET /posts/{id}
    // Devuelve el detalle de un post. El DTO incluye usuarioId y nombreUsuario
    // para que el frontend pueda mostrar el autor y volver a su perfil.
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> obtenerPostId(@PathVariable Integer id){
        Post post = postService.obtenerPostPorId(id);
        if(post==null){
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(postMapper.toDto(post));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> borrarPost(@PathVariable Integer id){
        boolean post = postService.borrarPost(id);
        if(post){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /posts/{id}
    // Actualiza un post completo usando los datos recibidos en el body.
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> actualizarPost(@PathVariable Integer id,@Valid @RequestBody CreatePostRequest request){
        Post post = new Post();
        post.setTitulo(request.getTitulo());
        post.setContenido(request.getContenido());
        Post actualizado = postService.actualizarPost(id,post);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postMapper.toDto(actualizado));
    }
}
