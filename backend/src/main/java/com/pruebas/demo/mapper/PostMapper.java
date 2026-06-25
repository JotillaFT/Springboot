package com.pruebas.demo.mapper;

import com.pruebas.demo.dto.CreatePostRequest;
import com.pruebas.demo.dto.PostResponseDTO;
import com.pruebas.demo.dto.PostSimpleDTO;
import com.pruebas.demo.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // MapStruct genera automaticamente la implementacion para convertir Post -> PostResponseDTO.
    @Mapping(source = "usuario.nombre", target = "nombreUsuario")
    PostResponseDTO toDto(Post post);

    // Convierte listas completas sin tener que hacer el bucle a mano.
    List<PostResponseDTO> toDtoList(List<Post> posts);

    // Version reducida de un post, util cuando el post aparece dentro de otro DTO.
    PostSimpleDTO toSimpleDto(Post post);

    // Convierte el DTO de entrada en entidad. El usuario se asigna aparte en el servicio o controller.
    Post toEntity(CreatePostRequest request);
}
