import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import {Post} from '../../model/post';
import {PostService} from '../../services/post.service';

@Component({
  selector: 'app-detalle-post',
  imports: [RouterLink],
  templateUrl: './detalle-post.html',
  styleUrl: './detalle-post.css',
})
export class DetallePost implements OnInit {
  post = signal<Post | null>(null);

  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
  ) {}

  ngOnInit():void {
    // Lee el parametro dinamico :id definido en app.routes.ts.
    const id = Number(this.route.snapshot.paramMap.get('id'));

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
