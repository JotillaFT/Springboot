package com.pruebas.demo.controller;
import com.pruebas.demo.dto.CreateUsuarioRequest;
import com.pruebas.demo.dto.PageResponse;
import com.pruebas.demo.dto.UsuarioConPostDTO;
import com.pruebas.demo.dto.UsuarioResponseDTO;
import com.pruebas.demo.entity.Usuario;
import com.pruebas.demo.mapper.UsuarioMapper;
import com.pruebas.demo.service.UsuarioService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

//    METODO PARA CONVERTIR LOS OBJETOS EN DTO, HA SIDO SUSTITUIDO POR LA CLASE USUARIO MAPPER
//    private UsuarioResponseDTO toDTO(Usuario usuario){
//        UsuarioResponseDTO dto = new UsuarioResponseDTO();
//
//        dto.setId(usuario.getId());
//        dto.setNombre(usuario.getNombre());
//
//        return dto;
//    }

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper){
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios(){
        List<Usuario> usuarios =  usuarioService.obtenerUsuarios();
        List<UsuarioResponseDTO> usuarioResponseDTOS = usuarioMapper.toDtoList(usuarios);
        return ResponseEntity.ok(usuarioResponseDTOS);
    }

    @GetMapping(value = "/usuarios", params = {"pagina","size","sort","direccion"})
    public ResponseEntity<PageResponse<UsuarioResponseDTO>> obtenerUsuariosPagina(
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direccion){
        PageResponse<UsuarioResponseDTO> response = new PageResponse<>();
        Page<Usuario> usuarios =  usuarioService.obtenerUsuarios(pagina,size,sort,direccion);
        List<UsuarioResponseDTO> usuarioResponseDTOS = usuarioMapper.toDtoList(usuarios.getContent());
        response.setContent(usuarioResponseDTOS);
        response.setTotalElements(usuarios.getTotalElements());
        response.setTotalPages(usuarios.getTotalPages());
        response.setPage(usuarios.getNumber());
        response.setSize(usuarios.getSize());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody CreateUsuarioRequest request){

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setEdad(request.getEdad());

        Usuario nuevoUsuario =  usuarioService.crearUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDto(nuevoUsuario));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Integer id){

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        return ResponseEntity.ok(usuarioMapper.toDto(usuario));
    }

    @GetMapping("usuarios/{id}/posts")
    public ResponseEntity<UsuarioConPostDTO> obtenerUsuarioConPosts(@PathVariable Integer id){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        UsuarioConPostDTO usuarioConPostDTO = usuarioMapper.toDtoConPosts(usuario);

        return ResponseEntity.ok(usuarioConPostDTO);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> borrarUsuario(@PathVariable Integer id){
        boolean usuario = usuarioService.borrarUsuario(id);
        if(usuario){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();

    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id,@RequestBody Usuario usuario){
        Usuario nuevoUsuario =  usuarioService.actualizarUsuario(id,usuario);
        if(nuevoUsuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/usuarios/count")
    public ResponseEntity<Integer> contarUsuarios(){
        long usuarios =  usuarioService.contarUsuarios();
        Integer usuariosint = (int) usuarios;
        return ResponseEntity.ok(usuariosint);
    }

    //Estos endpoints han sido sustituidos por el endpoint filtro, pero quedan de ejemplo para como serian segun los metodos service individuales
//    @GetMapping(value = "/usuarios",params = "nombre")
//    public ResponseEntity<List<Usuario>> buscadorNombre(@RequestParam String nombre){
//        List<Usuario>  usuarios =  usuarioService.buscarPorNombre(nombre);
//        if(usuarios == null){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(usuarios);
//    }

//    @GetMapping(value = "/usuarios/busqueda", params = "nombre")
//    public ResponseEntity<List<UsuarioResponseDTO>> buscadorNombreParcial(@RequestParam String nombre){
//        System.out.println("BUSQUEDA POR NOMBRE");
//        List<Usuario> usuarios = usuarioService.buscarPorNombreParcial(nombre);
//        List<UsuarioResponseDTO> usuariosDTO = new ArrayList<>();
//        for (Usuario usuario : usuarios){
//            usuariosDTO.add(toDTO(usuario));
//        }
//        return ResponseEntity.ok(usuariosDTO);
//
//    }

//    @GetMapping(value = "/usuarios/busqueda",params = {"min","max"})
//    public ResponseEntity<List<UsuarioResponseDTO>> buscadorEdadEntre(@RequestParam Integer min, @RequestParam Integer max){
//        System.out.println("BUSQUEDA POR EDAD");
//        List<Usuario> usuarios = usuarioService.buscarPorEdadEntre(min,max);
//        List<UsuarioResponseDTO> usuariosDTO = new ArrayList<>();
//        for (Usuario usuario : usuarios){
//            usuariosDTO.add(toDTO(usuario));
//        }
//        return ResponseEntity.ok(usuariosDTO);
//
//    }

    @GetMapping("usuarios/filtro")
    public ResponseEntity<PageResponse<UsuarioResponseDTO>> filtrar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer minEdad,
            @RequestParam(required = false) Integer maxEdad,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String orden,
            @RequestParam(defaultValue = "asc") String direccion){
        System.out.println("BUSQUEDA POR FILTRO");
        PageResponse<UsuarioResponseDTO> response = new PageResponse<>();
        Page<Usuario> usuarios = usuarioService.buscarPorFiltro(nombre,minEdad,maxEdad,page,size,orden,direccion);
        List<UsuarioResponseDTO> usuarioResponseDTOS = usuarioMapper.toDtoList(usuarios.getContent());
        response.setContent(usuarioResponseDTOS);
        response.setTotalElements(usuarios.getTotalElements());
        response.setTotalPages(usuarios.getTotalPages());
        response.setPage(usuarios.getNumber());
        response.setSize(usuarios.getSize());
        return ResponseEntity.ok(response);
    }


}
