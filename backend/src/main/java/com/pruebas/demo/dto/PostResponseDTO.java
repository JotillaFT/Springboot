package com.pruebas.demo.dto;

public class PostResponseDTO {
    // DTO de salida para posts.
    // Aplana datos del usuario propietario para evitar enviar la entidad Usuario completa.
    private Integer id;
    private String titulo;
    private String contenido;

    // Id del usuario propietario. El frontend lo usa, por ejemplo, para volver
    // desde el detalle del post al perfil del usuario.
    private Integer usuarioId;

    // Nombre visible del autor. Sale de Post.usuario.nombre mediante PostMapper.
    private String nombreUsuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
