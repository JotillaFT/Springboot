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

    UsuarioResponseDTO toDto(Usuario usuario);

    List<UsuarioResponseDTO> toDtoList(List<Usuario> usuarios);

    UsuarioConPostDTO toDtoConPosts(Usuario usuario);

    PostSimpleDTO toPostSimpleDto(Post post);
}
