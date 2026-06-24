package com.pruebas.demo.repository;

import com.pruebas.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

// JpaRepository aporta CRUD, paginacion y ordenacion sin escribir SQL.
// JpaSpecificationExecutor permite usar Specification para filtros dinamicos.
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {
    // Query Methods: Spring Data interpreta el nombre del metodo y genera la consulta.
    List<Usuario> findByNombre(String nombre);
    List<Usuario> findByNombreContaining(String nombre);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByNombreStartingWith(String nombre);
    List<Usuario> findByEdad(Integer edad);
    List<Usuario> findByEdadGreaterThan(Integer edad);
    List<Usuario> findByEdadBetween(Integer min, Integer max);
    List<Usuario> findByNombreContainingIgnoreCaseAndEdadBetween(
            String nombre,
            Integer min,
            Integer max
    );


}
