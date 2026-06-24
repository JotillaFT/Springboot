package com.pruebas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	// Punto de entrada del backend. Spring Boot arranca aqui y crea el contexto
	// de la aplicacion: controladores, servicios, repositorios, configuracion, etc.
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
