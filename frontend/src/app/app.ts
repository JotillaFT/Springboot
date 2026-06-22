import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

import { Usuario } from './model/usuario';
import { UsuarioService } from './services/usuario.service';

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    console.log('APP INICIADA');
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (datos) => {
        this.usuarios = datos;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }
}
