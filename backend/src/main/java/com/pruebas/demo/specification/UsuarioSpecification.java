package com.pruebas.demo.specification;

import com.pruebas.demo.entity.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {
    public static Specification<Usuario> nombreContiene(String nombre){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                );
    }

    public static Specification<Usuario> edadMinima(Integer edad){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("edad"),
                        edad
                );
    }

    public static Specification<Usuario> edadMaxima(Integer edad){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("edad"),
                        edad
                );
    }
}
