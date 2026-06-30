import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { PostService } from '../../services/post.service';
import { CrearPostRequest } from '../../model/crear-post-request';

@Component({
  // Pantalla de edicion de post. Carga el post actual y permite guardar titulo/contenido.
  selector: 'app-editar-post',
  imports: [FormsModule, RouterLink],
  templateUrl: './editar-post.html',
  styleUrl: './editar-post.css',
})
export class EditarPost implements OnInit {
  // id identifica el post que se edita; usuarioId sirve para volver al perfil tras guardar.
  id = 0;
  usuarioId = 0;

  // Campos enlazados con [(ngModel)] en editar-post.html.
  titulo = '';
  contenido = '';

  // Mensaje general para errores de carga, validacion o actualizacion.
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
  ) {}

  ngOnInit(): void {
    // Primero se lee el id de la URL y despues se pide el post para rellenar el formulario.
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('ID recibido:', this.id);

    this.postService.obtenerPost(this.id).subscribe({
      next: (post) => {
        this.titulo = post.titulo;
        this.contenido = post.contenido;
        this.usuarioId = post.usuarioId;
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo cargar el post';
      },
    });
  }

  actualizarPost(): void {
    // Validacion minima de formulario antes de llamar al PUT del backend.
    if (this.titulo === '' || this.contenido === '') {
      this.mensaje = 'Debes introducir un titulo y/o contenido vÃ¡lidos ';
      return;
    }

    // Crear y editar post comparten estructura: ambos envian titulo y contenido.
    const request: CrearPostRequest = {
      titulo: this.titulo,
      contenido: this.contenido,
    };

    this.postService.actualizarPost(this.id, request).subscribe({
      next: () => {
        this.router.navigate(['usuarios', this.usuarioId]);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'Error al actualizar el post';
      },
    });
  }
}
