import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../../services/usuario.service';
import { CrearUsuarioRequest } from '../../model/crear-usuario-request';

@Component({
  // Pagina con formulario template-driven para crear usuarios.
  selector: 'app-crear-usuario',
  imports: [FormsModule, RouterLink],
  templateUrl: './crear-usuario.html',
  styleUrl: './crear-usuario.css',
})
export class CrearUsuario {
  // Estas propiedades se enlazan con los inputs usando [(ngModel)] en el HTML.
  nombre = "";
  edad: number | null = null;

  // Mensaje visible cuando la validacion local o la peticion fallan.
  mensaje = "";

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  crearUsuario(): void {
    // Primera validacion en frontend antes de llamar al backend.
    // El backend tambien valida, asi que hay doble proteccion.
    if(this.nombre.trim() === '' || this.edad === null  ||  this.edad < 0 || this.edad > 120){
      this.mensaje = 'Debes introducir un nombre válido y una edad entre 0 y 120';
      return;
    }

    // Objeto que se enviara como JSON a POST /usuarios.
    const  request: CrearUsuarioRequest = {
      nombre: this.nombre,
      edad: this.edad
    };

    // Si el usuario se crea correctamente, navega de vuelta al listado.
    this.usuarioService.crearUsuario(request).subscribe({
      next: (usuarioCreado) => {
        console.log('Usuario creado', usuarioCreado);
        this.router.navigate(['/usuarios']);
      },
      error:(error) =>{
        console.error(error);
        this.mensaje = 'Error al crear el usuario';
      }
      });
  }
}
