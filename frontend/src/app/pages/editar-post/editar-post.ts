import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import {PostService} from '../../services/post.service';
import {CrearPostRequest} from '../../model/crear-post-request';


@Component({
  selector: 'app-editar-post',
  imports: [ FormsModule, RouterLink],
  templateUrl: './editar-post.html',
  styleUrl: './editar-post.css',
})
export class EditarPost implements OnInit {
  id = 0;
  usuarioId = 0;
  titulo = '';
  contenido = '';

  mensaje= ""

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService,
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('ID recibido:', this.id);
    this.postService.obtenerPost(this.id).subscribe({
      next:(post)=>{
        this.titulo = post.titulo;
        this.contenido = post.contenido;
        this.usuarioId = post.usuarioId;
    },
      error: (error) =>{
        console.error(error);
        this.mensaje = 'No se pudo cargar el post'
      }

    });
  }

  actualizarPost(){
    if(this.titulo === '' || this.contenido === ''){
      this.mensaje = 'Debes introducir un titulo y/o contenido válidos ';
      return;
    }

    const request: CrearPostRequest = {
      titulo:  this.titulo,
      contenido:  this.contenido
    };

    this.postService.actualizarPost(this.id,request).subscribe({
      next: ()=>{
        this.router.navigate(['usuarios', this.usuarioId])
      },
      error: (error) =>{
        console.error(error);
        this.mensaje = "Error al actualizar el post"
      }
    })
  }
}
