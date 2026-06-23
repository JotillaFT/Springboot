import { Component, OnInit, signal } from '@angular/core';
import { Usuario } from '../../model/usuario';
import { UsuarioService } from '../../services/usuario.service';


@Component({
  selector: 'app-usuarios',
  imports: [],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css',
})
export class Usuarios implements OnInit {
  usuarios = signal<Usuario[]>([]);

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    console.log('APP INICIADA');

    this.usuarioService.obtenerUsuarios().subscribe({
      next: (datos) => {
        console.log('USUARIOS RECIBIDOS', datos);
        this.usuarios.set(datos);
        console.log('TOTAL GUARDADO', this.usuarios().length);
      },
      error: (error) => {
        console.error(error);
      },
    });
  }
}
