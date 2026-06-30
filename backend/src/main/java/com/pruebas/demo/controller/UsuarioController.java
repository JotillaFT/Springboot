package com.pruebas.demo.controller;
import com.pruebas.demo.dto.CreateUsuarioRequest;
import com.pruebas.demo.dto.PageResponse;
import com.pruebas.demo.dto.UsuarioConPostDTO;
import com.pruebas.demo.dto.UsuarioResponseDTO;
import com.pruebas.demo.entity.Usuario;
import com.pruebas.demo.mapper.UsuarioMapper;
import com.pruebas.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    // El controlador recibe peticiones HTTP y delega el trabajo real al servicio.
    private final UsuarioService usuarioService;

    // El mapper convierte entidades de base de datos en DTOs para devolver solo
    // los datos que quiere exponer la API.
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

    // GET /usuarios
    // Devuelve todos los usuarios sin paginacion.
    @GetMapping(value= "/usuarios", params = "!pagina")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios(){
        List<Usuario> usuarios =  usuarioService.obtenerUsuarios();
        List<UsuarioResponseDTO> usuarioResponseDTOS = usuarioMapper.toDtoList(usuarios);
        return ResponseEntity.ok(usuarioResponseDTOS);
    }

    // GET /usuarios?pagina=0&size=10&sort=id&direccion=asc
    // Devuelve usuarios paginados y ordenados. PageResponse es tu envoltorio
    // propio para enviar contenido + metadatos de paginacion al frontend.
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

    // POST /usuarios
    // @Valid activa las validaciones definidas en CreateUsuarioRequest.
    // @RequestBody indica que los datos vienen en el cuerpo JSON de la peticion.
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody CreateUsuarioRequest request){

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setEdad(request.getEdad());

        Usuario nuevoUsuario =  usuarioService.crearUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDto(nuevoUsuario));
    }

    // GET /usuarios/{id}
    // @PathVariable toma el id directamente de la URL.
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Integer id){

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        return ResponseEntity.ok(usuarioMapper.toDto(usuario));
    }

    // GET /usuarios/{id}/posts
    // Devuelve un usuario junto con una version simplificada de sus posts.
    @GetMapping("usuarios/{id}/posts")
    public ResponseEntity<UsuarioConPostDTO> obtenerUsuarioConPosts(@PathVariable Integer id){
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        UsuarioConPostDTO usuarioConPostDTO = usuarioMapper.toDtoConPosts(usuario);

        return ResponseEntity.ok(usuarioConPostDTO);
    }

    // DELETE /usuarios/{id}
    // Si el servicio confirma que existia, la API devuelve 204 No Content.
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable Integer id){
        boolean usuario = usuarioService.borrarUsuario(id);
        if(usuario){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();

    }

    // PUT /usuarios/{id}
    // Actualiza un usuario completo usando los datos recibidos en el body.
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Integer id,@Valid @RequestBody CreateUsuarioRequest request){
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEdad(request.getEdad());

        Usuario nuevoUsuario =  usuarioService.actualizarUsuario(id,usuario);
        if(nuevoUsuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioMapper.toDto(nuevoUsuario));
    }

    // GET /usuarios/count
    // Ejemplo de endpoint que devuelve un dato agregado en lugar de una lista.
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

    // GET /usuarios/filtro?nombre=ana&minEdad=18&maxEdad=65&page=0&size=10
    // Usa Specifications para montar una consulta dinamica: solo se aplican
    // los filtros que llegan informados en la URL.
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
