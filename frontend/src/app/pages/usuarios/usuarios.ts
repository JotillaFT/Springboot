import { Component, OnInit, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { Usuario } from '../../model/usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  // Pagina que muestra el listado de usuarios recibido desde el backend.
  selector: 'app-usuarios',
  imports: [RouterLink],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css',
})
export class Usuarios implements OnInit {
  // signal guarda estado reactivo. Cuando cambia con .set(...), Angular actualiza la vista.
  usuarios = signal<Usuario[]>([]);

  constructor(private usuarioService: UsuarioService) {}

  // ngOnInit se ejecuta una vez cuando Angular crea el componente.
  ngOnInit(): void {
    console.log('APP INICIADA');

    // Llamada HTTP asincrona al backend. subscribe define que hacer si va bien o si falla.
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
