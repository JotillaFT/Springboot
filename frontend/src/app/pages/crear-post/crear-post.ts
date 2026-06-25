import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { ActivatedRoute, Router,RouterLink} from '@angular/router';

import {PostService} from '../../services/post.service';
import {CrearPostRequest} from '../../model/crear-post-request';

@Component({
  selector: 'app-crear-post',
  imports: [FormsModule, RouterLink],
  templateUrl: './crear-post.html',
  styleUrl: './crear-post.css',
})
export class CrearPost {
  titulo = '';
  contenido = '';

  mensaje = '';

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  usuarioId=0
  ngOnInit(): void {
    // Lee el parametro dinamico :id definido en app.routes.ts.
    this.usuarioId = Number(this.route.snapshot.paramMap.get('id'));
    }
  crearPost():void{
    if (this.titulo.trim() === '' || this.contenido.trim() === '') {
      this.mensaje = 'Debes introducir un titulo y un contenido para el post';
      return;
    }

    const request: CrearPostRequest = { titulo: this.titulo, contenido: this.contenido };

    this.postService.crearPost(this.usuarioId, request).subscribe({
      next: (postCreado) => {
        console.log('Post Creado', postCreado);
        this.router.navigate(['/usuarios', this.usuarioId]);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'Error al crear el post';
      },
    });
  }
}
