import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { ActivatedRoute, Router,RouterLink} from '@angular/router';

import {PostService} from '../../services/post.service';
import {CrearPostRequest} from '../../model/crear-post-request';

@Component({
  // Formulario para crear un post dentro del perfil de un usuario.
  selector: 'app-crear-post',
  imports: [FormsModule, RouterLink],
  templateUrl: './crear-post.html',
  styleUrl: './crear-post.css',
})
export class CrearPost {
  // Campos enlazados con [(ngModel)] en crear-post.html.
  titulo = '';
  contenido = '';

  // Mensaje general para validaciones propias o errores de backend.
  mensaje = '';

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  // Se guarda el id del usuario de la ruta para crear el post y para volver al perfil.
  usuarioId=0

  ngOnInit(): void {
    // Lee el parametro dinamico :id definido en app.routes.ts.
    this.usuarioId = Number(this.route.snapshot.paramMap.get('id'));
    }

  crearPost():void{
    // Validacion rapida en frontend antes de enviar la peticion.
    // El backend tambien valida con CreatePostRequest.
    if (this.titulo.trim() === '' || this.contenido.trim() === '') {
      this.mensaje = 'Debes introducir un titulo y un contenido para el post';
      return;
    }

    // El request no incluye usuarioId: el backend lo recibe en la URL.
    const request: CrearPostRequest = { titulo: this.titulo, contenido: this.contenido };

    this.postService.crearPost(this.usuarioId, request).subscribe({
      next: (postCreado) => {
        console.log('Post Creado', postCreado);

        // Al terminar, vuelve al detalle del usuario para ver el nuevo post en su lista.
        this.router.navigate(['/usuarios', this.usuarioId]);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'Error al crear el post';
      },
    });
  }
}
