import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { PostService } from '../../services/post.service';
import { Post } from '../../model/post';

@Component({
  // Pantalla de confirmacion antes de borrar un post.
  selector: 'app-borrar-post',
  imports: [RouterLink],
  templateUrl: './borrar-post.html',
  styleUrl: './borrar-post.css',
})
export class BorrarPost implements OnInit {
  // id viene de /posts/:id/borrar.
  id = 0;

  // Se carga el post para mostrar que se va a borrar y para conocer su usuarioId.
  post = signal<Post | null>(null);
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
  ) {}

  ngOnInit(): void {
    // Antes de permitir borrar, se pide al backend el post que se va a eliminar.
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
    // Si el DELETE termina bien, volvemos al perfil del usuario propietario.
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
