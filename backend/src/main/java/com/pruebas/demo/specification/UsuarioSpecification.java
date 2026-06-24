package com.pruebas.demo.specification;

import com.pruebas.demo.entity.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {
    // Cada metodo devuelve una Specification reutilizable.
    // Luego UsuarioService las combina con .and(...) segun los filtros recibidos.

    // WHERE lower(nombre) LIKE '%texto%'
    public static Specification<Usuario> nombreContiene(String nombre){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                );
    }

    // WHERE edad >= valor
    public static Specification<Usuario> edadMinima(Integer edad){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("edad"),
                        edad
                );
    }

    // WHERE edad <= valor
    public static Specification<Usuario> edadMaxima(Integer edad){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("edad"),
                        edad
                );
    }
}
