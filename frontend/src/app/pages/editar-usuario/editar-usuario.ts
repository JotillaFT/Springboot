import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../../services/usuario.service';
import { CrearUsuarioRequest } from '../../model/crear-usuario-request';

@Component({
  // Pantalla de edicion. Primero carga el usuario y luego permite guardar cambios.
  selector: 'app-editar-usuario',
  imports: [ FormsModule, RouterLink],
  templateUrl: './editar-usuario.html',
  styleUrl: './editar-usuario.css',
})
export class EditarUsuario implements OnInit {
  // El id viene de /usuarios/:id/editar y se reutiliza al actualizar y al volver al detalle.
  id = 0;

  // Campos del formulario enlazados con [(ngModel)].
  nombre = '';
  edad: number | null = null;

  // Mensaje para errores de carga, validacion o actualizacion.
  mensaje = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
  ) {}

  ngOnInit(): void {
    // Carga inicial: se lee el usuario actual para rellenar el formulario.
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
    // Validacion local antes de enviar el PUT. El backend debe seguir siendo la validacion definitiva.
    if (this.nombre.trim() === '' || this.edad === null || this.edad < 0 || this.edad > 120) {
      this.mensaje = 'Debes introducir un nombre válido y una edad entre 0 y 120';
      return;
    }

    // Se reutiliza el mismo modelo que en creacion porque ambos envian nombre y edad.
    const request: CrearUsuarioRequest ={
      nombre: this.nombre,
      edad: this.edad,
    };

    this.usuarioService.actualizarUsuario(this.id, request).subscribe({
      next: () => {
        // Despues de guardar, vuelve al detalle del usuario actualizado.
        this.router.navigate(['/usuarios', this.id]);
      },
      error: (error) => {
        console.error(error);
        this.mensaje = 'Error al actualizar el usuario';
      },
    });
  }
}
