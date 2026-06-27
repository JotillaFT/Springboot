import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { PostService } from '../../services/post.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-borrar-post',
  imports: [RouterLink],
  templateUrl: './borrar-post.html',
  styleUrl: './borrar-post.css',
})
export class BorrarPost implements OnInit {
  id = 0;
  post = signal<Post | null>(null);
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.postService.obtenerPost(this.id).subscribe({
      next: (post) => {
        this.post.set(post);
      },
      error: (error) => {
        console.log(error);
        this.mensaje = 'No se puede cargar el post';
      },
    });
  }

  borrarPost(): void {
    // Si el DELETE termina bien, volvemos al listado porque el detalle ya no existe.
    this.postService.borrarPost(this.id).subscribe({
      next: () => {
        this.router.navigate(['/usuarios', this.post()!.usuarioId]);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo borrar el post';
      },
    });
  }
}
