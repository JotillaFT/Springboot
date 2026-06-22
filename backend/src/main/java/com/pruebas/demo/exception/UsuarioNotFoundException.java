package com.pruebas.demo.exception;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(Integer id){
        super("Usuario con id " + id + " no encontrado");
    }

}
