import { Component, OnInit, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { Usuario } from '../../model/usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  // Pagina que muestra el listado de usuarios recibido desde el backend.
  selector: 'app-usuarios',
  imports: [RouterLink],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.css',
})
export class Usuarios implements OnInit {
  // signal guarda estado reactivo. Cuando cambia con .set(...), Angular actualiza la vista.
  usuarios = signal<Usuario[]>([]);

  // Estado de paginacion que se envia al backend en cada carga.
  // Si cambias size en el futuro, el HTML tambien lo usa para reservar espacio visual en la lista.
  pagina = 0;
  size = 5;
  sort = 'id';
  direccion = 'asc';

  totalPages = 0;
  totalElements = 0;

  constructor(private usuarioService: UsuarioService) {}

  // ngOnInit se ejecuta una vez cuando Angular crea el componente.
  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    // Cada cambio de pagina vuelve a pedir datos al backend; el frontend no pagina en memoria.
    this.usuarioService
      .obtenerUsuariosPaginados(this.pagina, this.size, this.sort, this.direccion)
      .subscribe({
        next: (response) => {
          this.usuarios.set(response.content);

          // Estos metadatos alimentan el contador y habilitan/deshabilitan botones.
          this.totalPages = response.totalPages;
          this.totalElements = response.totalElements;
        },
        error: (error) => {
          console.error(error);
        },
      });
  }

  anterior(): void {
    // Guardia defensiva: aunque el boton este disabled, evita bajar de la pagina 0.
    if (this.pagina == 0) {
      return;
    }
    this.pagina--;
    this.cargarUsuarios();
  }

  siguiente(): void {
    // totalPages viene del backend; si estamos en la ultima pagina no avanzamos mas.
    if (this.pagina == this.totalPages - 1) {
      return;
    }
    this.pagina++;
    this.cargarUsuarios();
  }
}
