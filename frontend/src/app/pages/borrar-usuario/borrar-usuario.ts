import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../model/usuario';

@Component({
  // Pantalla de confirmacion antes de borrar un usuario.
  selector: 'app-borrar-usuario',
  imports: [RouterLink],
  templateUrl: './borrar-usuario.html',
  styleUrl: './borrar-usuario.css',
})
export class BorrarUsuario implements OnInit {
  // Id recibido de /usuarios/:id/borrar.
  id = 0;

  // Se carga el usuario para mostrar que registro se va a eliminar.
  usuario = signal<Usuario | null>(null);

  // Mensaje visible si falla la carga o el borrado.
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit(): void {
    // Antes de mostrar la confirmacion, pedimos los datos del usuario.
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.usuarioService.obtenerUsuarioPorId(this.id).subscribe({
      next: (usuario) => {
        this.usuario.set(usuario);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo cargar el usuario';
      },
    });
  }

  borrarUsuario(): void {
    // Si el DELETE termina bien, volvemos al listado porque el detalle ya no existe.
    this.usuarioService.borrarUsuario(this.id).subscribe({
      next: () => {
        this.router.navigate(['/usuarios']);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo borrar el usuario';
      },
    });
  }
}
