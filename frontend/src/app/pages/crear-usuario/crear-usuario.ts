import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../../services/usuario.service';
import { CrearUsuarioRequest } from '../../model/crear-usuario-request';

@Component({
  selector: 'app-crear-usuario',
  imports: [FormsModule, RouterLink],
  templateUrl: './crear-usuario.html',
  styleUrl: './crear-usuario.css',
})
export class CrearUsuario {
  nombre = "";
  edad: number | null = null;
  mensaje = "";

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  crearUsuario(): void {
    if(this.nombre.trim() === '' || this.edad === null){
      this.mensaje = 'Debes rellenar nombre y edad';
      return;
    }

    const  request: CrearUsuarioRequest = {
      nombre: this.nombre,
      edad: this.edad
    };

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
