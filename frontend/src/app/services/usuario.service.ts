import { Injectable, Service } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import {Usuario} from '../model/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService{

  private apiUrl = 'http://localhost:8080'

  constructor(private http: HttpClient) {}

  obtenerUsuarios(): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`);
  }
}
