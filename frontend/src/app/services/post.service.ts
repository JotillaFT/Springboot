import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {CrearPostRequest} from '../model/crear-post-request';
import {Post} from '../model/post';

@Injectable({
  providedIn: 'root',
})

export class PostService {
  // URL base del backend Spring Boot.
  private apiUrl = 'http://localhost:8080';

  // HttpClient es el objeto de Angular para hacer peticiones HTTP.
  constructor(private http: HttpClient) {}

  // POST /usuarios/{id}/posts
  // Crea un post para un usuario concreto. El usuarioId viaja en la URL.
  crearPost(usuarioId: number,request: CrearPostRequest): Observable<Post>{
    return this.http.post<Post>(`${this.apiUrl}/usuarios/${usuarioId}/posts`, request);
  }

  // GET /posts/{id}
  // Recupera el detalle completo de un post para la pantalla DetallePost.
  obtenerPost(postID: number): Observable<Post>{
    return this.http.get<Post>(`${this.apiUrl}/posts/${postID}`);
  }
}
