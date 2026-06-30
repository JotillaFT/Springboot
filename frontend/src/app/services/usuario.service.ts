import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../model/usuario';
import { CrearUsuarioRequest } from '../model/crear-usuario-request';
import { UsuarioConPosts } from '../model/usuario-con-posts';
import { PageResponse } from '../model/page-response';

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

  // GET /usuarios/{id}/posts
  // Este DTO combina datos del usuario con posts reducidos para la pantalla de detalle.
  obtenerUsuarioConPosts(id: number): Observable<UsuarioConPosts> {
    return this.http.get<UsuarioConPosts>(`${this.apiUrl}/usuarios/${id}/posts`);
  }

  // PUT /usuarios/{id}
  // Reutiliza CrearUsuarioRequest porque crear y editar envian los mismos campos.
  actualizarUsuario(id: number, request: CrearUsuarioRequest): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/usuarios/${id}`, request);
  }

  // DELETE /usuarios/{id}
  // Devuelve void porque si todo va bien el backend responde 204 No Content.
  borrarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/usuarios/${id}`);
  }

  // GET /usuarios paginado.
  // El backend devuelve content + metadatos; PageResponse<T> refleja esa forma generica.
  obtenerUsuariosPaginados(
    pagina: number,
    size: number,
    sort: string,
    direccion: string,
  ): Observable<PageResponse<Usuario>> {
    return this.http.get<PageResponse<Usuario>>(
      `${this.apiUrl}/usuarios?pagina=${pagina}&size=${size}&sort=${sort}&direccion=${direccion}`,
    );
  }
}
