import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../../services/usuario.service';
import { CrearUsuarioRequest } from '../../model/crear-usuario-request';
import { Usuarios } from '../usuarios/usuarios';

@Component({
  selector: 'app-editar-usuario',
  imports: [RouterLink, FormsModule],
  templateUrl: './editar-usuario.html',
  styleUrl: './editar-usuario.css',
})
export class EditarUsuario implements OnInit {
  id = 0;
  nombre = '';
  edad: number | null = null;
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.usuarioService.obtenerUsuarioPorId(this.id).subscribe({
      next: (usuario) => {
        this.nombre = usuario.nombre;
        this.edad = usuario.edad;
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'No se pudo cargar el usuario';
      },
    });
  }

  actualizarUsuario(): void {
    if (this.nombre.trim() === '' || this.edad === null || this.edad < 0 || this.edad > 120) {
      this.mensaje = 'Debes introducir un nombre válido y una edad entre 0 y 120';
      return;
    }

    const request: CrearUsuarioRequest = {
      nombre: this.nombre,
      edad: this.edad,
    };

    this.usuarioService.actualizarUsuario(this.id, request).subscribe({
      next: () => {
        this.router.navigate(['/usuarios', this.id]);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'Error al actualizar el usuario';
      },
    });
  }
}
