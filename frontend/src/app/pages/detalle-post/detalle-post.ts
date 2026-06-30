import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { Post } from '../../model/post';
import { PostService } from '../../services/post.service';
import { PostSimple } from '../../model/post-simple';
import { UsuarioService } from '../../services/usuario.service';

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
  postsDelUsuario = signal<PostSimple[]>([]);

  // Signals que controlan los botones de navegacion entre posts del mismo usuario.
  // null significa que no existe post disponible en esa direccion.
  postAnteriorId = signal<number | null>(null);
  postSiguienteId = signal<number | null>(null);

  // Mensaje visible si la peticion falla.
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit(): void {
    // Lee el parametro dinamico :id definido en app.routes.ts.
    this.route.paramMap.subscribe((params) => {
      const id = Number(params.get('id'));
      this.cargarPost(id);
    });
  }

  cargarPost(id: number): void {
    // Limpia destinos anteriores mientras se carga el nuevo post para evitar clicks obsoletos.
    this.postAnteriorId.set(null);
    this.postSiguienteId.set(null);
    this.postService.obtenerPost(id).subscribe({
      next: (post) => {
        this.post.set(post);

        // Para calcular anterior/siguiente necesitamos conocer todos los posts del mismo usuario.
        this.usuarioService.obtenerUsuarioConPosts(post.usuarioId).subscribe({
          next: (usuario) => {
            this.postsDelUsuario.set(usuario.posts);
            this.calcularNavegacion(post.id, usuario.posts);
          },
          error: (error) => {
            console.error(error);
            this.mensaje = 'No se pudieron cargar los posts del usuario';
          },
        });
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo cargar el post';
      },
    });
  }
  calcularNavegacion(postId: number, posts: PostSimple[]): void {
    // Orden estable por id: asi anterior/siguiente no depende del orden recibido del backend.
    const postsOrdenados = [...posts].sort((a, b) => a.id - b.id);

    const index = postsOrdenados.findIndex((post) => post.id === postId);

    // Si por cualquier motivo el post no aparece en la lista del usuario, se desactiva la navegacion.
    if (index === -1) {
      this.postAnteriorId.set(null);
      this.postSiguienteId.set(null);
      return;
    }

    this.postAnteriorId.set(index > 0 ? postsOrdenados[index - 1].id : null);

    this.postSiguienteId.set(
      index < postsOrdenados.length - 1 ? postsOrdenados[index + 1].id : null,
    );

    console.log('INDEX:', index);
    console.log('ANTERIOR:', this.postAnteriorId());
    console.log('SIGUIENTE:', this.postSiguienteId());
  }

  siguiente(): void {
    const id = this.postSiguienteId();

    // Guardia defensiva: el boton tambien esta disabled, pero asi el metodo es seguro por si se llama.
    if (id === null) {
      return;
    }

    this.postAnteriorId.set(null);
    this.postSiguienteId.set(null);

    this.router.navigate(['/posts', id]);
  }

  anterior(): void {
    const id = this.postAnteriorId();

    // Misma proteccion que en siguiente(): no hay ruta valida si no existe post anterior.
    if (id === null) {
      return;
    }
    this.postAnteriorId.set(null);
    this.postSiguienteId.set(null);

    this.router.navigate(['/posts', id]);
  }
}

/* Cuando llega el DTO Post, la signal se actualiza y el HTML pinta el contenido.
      this.postService.obtenerPost(id).subscribe({
        next: (datos) => {
          this.post.set(datos);
        },
        error: (error) => {
          console.error(error);
          this.mensaje = 'No se pudo cargar el post';
        },
      });
    });*/
