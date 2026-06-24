import { Injectable } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import {Usuario} from '../model/usuario';
import { CrearUsuarioRequest } from '../model/crear-usuario-request';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  // URL base del backend Spring Boot.
  private apiUrl = 'http://localhost:8080';

  // HttpClient es el objeto de Angular para hacer peticiones HTTP.
  constructor(private http: HttpClient) {}

  // GET /usuarios
  // Observable representa una respuesta asincrona: el componente se suscribe para recibirla.
  obtenerUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`);
  }

  // POST /usuarios
  // Envia al backend el DTO de creacion y espera como respuesta el usuario creado.
  crearUsuario(request: CrearUsuarioRequest): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/usuarios`, request);
  }

  // GET /usuarios/{id}
  // Recupera un usuario concreto para la pantalla de detalle.
  obtenerUsuarioPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/usuarios/${id}`);
  }
}
