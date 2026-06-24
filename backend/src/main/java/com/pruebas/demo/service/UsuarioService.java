package com.pruebas.demo.service;

import com.pruebas.demo.exception.UsuarioNotFoundException;
import com.pruebas.demo.entity.Usuario;
import com.pruebas.demo.repository.UsuarioRepository;
import com.pruebas.demo.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    // El servicio contiene la logica de negocio y usa el repositorio para hablar con la base de datos.
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    // Version paginada: Spring Data recibe un Pageable con pagina, tamano y orden.
    public Page<Usuario> obtenerUsuarios(Integer page, Integer size, String orden, String direccion){
        Sort sort = Sort.by(Sort.Direction.fromString(direccion), orden);
        Pageable pageable =  PageRequest.of(page,size,sort);
        Page<Usuario> pagina = usuarioRepository.findAll(pageable);
        return pagina;
    }

    public Usuario crearUsuario(Usuario usuario){
        // save inserta si no hay id, o actualiza si la entidad ya existe.
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario obtenerUsuarioPorId(int id){

        // Si no existe, lanzamos una excepcion propia que luego captura GlobalExceptionHandler.
        return usuarioRepository.findById(id).orElseThrow(() ->
                new UsuarioNotFoundException(id));
    }

    public boolean borrarUsuario(int id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario actualizarUsuario(int id, Usuario usuarioActualizado){
        // Optional evita trabajar directamente con null al buscar por id.
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isPresent()) {

            Usuario usuario = usuarioOptional.get();

            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEdad(usuarioActualizado.getEdad());

            return usuarioRepository.save(usuario);
        }

        return null;
    }

    public long contarUsuarios(){
        return usuarioRepository.count();
    }
    // Version con Query Methods
//    public List<Usuario> buscarPorNombre(String nombre){
//        return usuarioRepository.findByNombre(nombre);
//    }
//
//    public List<Usuario> buscarPorNombreParcial(String nombre){ return usuarioRepository.findByNombreContainingIgnoreCase(nombre);}
//
//    public List<Usuario> buscarPorEdadEntre(Integer min, Integer max){ return usuarioRepository.findByEdadBetween(min,max);}

//    public List<Usuario> buscarPorFiltro(String nombre, Integer min, Integer max){
//        if(nombre == null && min == null && max == null){
//            return usuarioRepository.findAll();
//        }
//
//        if(nombre != null && min == null && max == null){
//            return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
//        }
//
//        if(nombre == null && min != null && max != null){
//            return usuarioRepository.findByEdadBetween(min,max);
//        }
//
//        if(nombre != null && min != null && max != null){
//            return usuarioRepository.findByNombreContainingIgnoreCaseAndEdadBetween(nombre,min,max);
//        }
//
//        return Collections.emptyList();
//    }

    // Version con Specification.
    // Empieza sin restricciones y va anadiendo filtros segun los parametros recibidos.
    public Page<Usuario> buscarPorFiltro(String nombre, Integer min, Integer max,Integer page, Integer size, String orden, String direccion){
        Sort sort = Sort.by(Sort.Direction.fromString(direccion), orden);
        Pageable pageable =  PageRequest.of(page,size,sort);
        Specification<Usuario> spec = Specification.unrestricted();
        if(nombre !=null){
            spec = spec.and(UsuarioSpecification.nombreContiene(nombre));
        }
        if(min != null){
            spec = spec.and(UsuarioSpecification.edadMinima(min));
        }
        if(max != null){
            spec = spec.and(UsuarioSpecification.edadMaxima(max));
        }

        return usuarioRepository.findAll(spec,pageable);
    }

}
