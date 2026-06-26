import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import {Post} from '../../model/post';
import {PostService} from '../../services/post.service';

@Component({
  // Pagina de detalle de un post. Lee /posts/:id y pide sus datos al backend.
  selector: 'app-detalle-post',
  imports: [RouterLink],
  templateUrl: './detalle-post.html',
  styleUrl: './detalle-post.css',
})
export class DetallePost implements OnInit {
  // null representa el estado inicial mientras no ha llegado la respuesta HTTP.
  post = signal<Post | null>(null);

  // Mensaje visible si la peticion falla.
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
  ) {}

  ngOnInit():void {
    // Lee el parametro dinamico :id definido en app.routes.ts.
    const id = Number(this.route.snapshot.paramMap.get('id'));

    // Cuando llega el DTO Post, la signal se actualiza y el HTML pinta el contenido.
    this.postService.obtenerPost(id).subscribe({
      next:(datos)=>{
      this.post.set(datos);
    },
      error:(error)=> {
        console.error(error);
        this.mensaje = "No se pudo cargar el post";
      }
      });
  }
}
