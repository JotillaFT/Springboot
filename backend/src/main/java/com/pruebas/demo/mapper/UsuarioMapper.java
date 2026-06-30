package com.pruebas.demo.mapper;

import com.pruebas.demo.dto.PostSimpleDTO;
import com.pruebas.demo.dto.UsuarioConPostDTO;
import com.pruebas.demo.dto.UsuarioResponseDTO;
import com.pruebas.demo.entity.Post;
import com.pruebas.demo.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // MapStruct genera automaticamente la implementacion para convertir Usuario -> UsuarioResponseDTO.
    UsuarioResponseDTO toDto(Usuario usuario);

    // Convierte listas completas sin tener que hacer el bucle a mano.
    List<UsuarioResponseDTO> toDtoList(List<Usuario> usuarios);

    // Variante del DTO que incluye tambien los posts del usuario.
    // MapStruct usa toPostSimpleDto para convertir cada Post de la lista.
    UsuarioConPostDTO toDtoConPosts(Usuario usuario);

    // Conversion usada dentro de UsuarioConPostDTO para no enviar el post completo.
    PostSimpleDTO toPostSimpleDto(Post post);
}
