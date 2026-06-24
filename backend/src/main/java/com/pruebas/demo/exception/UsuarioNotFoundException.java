package com.pruebas.demo.exception;

public class UsuarioNotFoundException extends RuntimeException{
    // Excepcion propia para expresar un caso de negocio: se pidio un usuario que no existe.
    public UsuarioNotFoundException(Integer id){
        super("Usuario con id " + id + " no encontrado");
    }

}
