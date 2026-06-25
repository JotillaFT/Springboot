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

  //POST posts
  crearPost(usuarioId: number,request: CrearPostRequest): Observable<Post>{
    return this.http.post<Post>(`${this.apiUrl}/usuario/${usuarioId}/posts`, request);
  }
}
