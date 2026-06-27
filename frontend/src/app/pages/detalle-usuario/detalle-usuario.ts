import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { Usuario } from '../../model/usuario';
import { UsuarioService } from '../../services/usuario.service';
import { UsuarioConPosts } from '../../model/usuario-con-posts';

@Component({
  // Pagina de detalle. Lee el id de la URL y pide ese usuario al backend.
  selector: 'app-detalle-usuario',
  imports: [RouterLink],
  templateUrl: './detalle-usuario.html',
  styleUrl: './detalle-usuario.css',
})
export class DetalleUsuario implements OnInit {
  // null representa el estado inicial mientras todavia no ha llegado la respuesta.
  usuario = signal<UsuarioConPosts | null>(null);

  // Mensaje para mostrar errores de carga en la plantilla.
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit(): void {
    // Lee el parametro dinamico :id definido en app.routes.ts.
    const id = Number(this.route.snapshot.paramMap.get('id'));

    // Pide el usuario al backend y actualiza la signal cuando llega la respuesta.
    this.usuarioService.obtenerUsuarioConPosts(id).subscribe({
      next: (datos) => {
        this.usuario.set(datos);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo cargar el usuario';
      },
    });
  }
}
