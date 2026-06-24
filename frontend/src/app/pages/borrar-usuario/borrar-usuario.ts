import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../../services/usuario.service';
import { Usuarios } from '../usuarios/usuarios';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-borrar-usuario',
  imports: [RouterLink],
  templateUrl: './borrar-usuario.html',
  styleUrl: './borrar-usuario.css',
})
export class BorrarUsuario implements OnInit {
  id = 0;
  usuario = signal<Usuario | null>(null);
  mensaje = "";


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.usuarioService.obtenerUsuarioPorId(this.id).subscribe({
      next:(usuario) =>{
          this.usuario.set(usuario);
      },
      error : (error) =>{
        console.error(error);
        this.mensaje = "No se pudo cargar el usuario";
      }
    });
  }

  borrarUsuario(): void{
    this.usuarioService.borrarUsuario(this.id).subscribe({
      next: () =>{
        this.router.navigate(["/usuarios"]);
      },
      error: (error) =>{
        console.error(error);
        this.mensaje = "No se pudo borrar el usuario";
      }
    });
  }

}
