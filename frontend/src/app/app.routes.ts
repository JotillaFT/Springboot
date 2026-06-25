import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Usuarios } from './pages/usuarios/usuarios';
import { CrearUsuario } from './pages/crear-usuario/crear-usuario';
import { DetalleUsuario } from './pages/detalle-usuario/detalle-usuario';
import { EditarUsuario } from './pages/editar-usuario/editar-usuario';
import { BorrarUsuario } from './pages/borrar-usuario/borrar-usuario';
import {CrearPost} from './pages/crear-post/crear-post';
import { DetallePost } from './pages/detalle-post/detalle-post';

// Tabla de rutas de la aplicacion.
// Cada path indica que componente se debe mostrar cuando cambia la URL.
export const routes: Routes = [
  // Ruta inicial: http://localhost:4200/
  { path: '', component: Home },

  // Listado de usuarios.
  { path: 'usuarios', component: Usuarios },

  // Formulario para crear un usuario nuevo.
  { path: 'usuarios/nuevo', component: CrearUsuario },

  // Modifica un usuario en concreto siendo :id un parametro dinamico
  { path: 'usuarios/:id/editar', component: EditarUsuario },

  //Borra un usuario concreto segun su id
  { path: 'usuarios/:id/borrar', component: BorrarUsuario },
  //Crea un post de un usuario en concreto

  {path:'usuarios/:id/posts/nuevo', component: CrearPost},

  // Detalles de un post concreto segun id
  {path: 'posts/:id', component: DetallePost},
  // Detalle de un usuario concreto. :id es un parametro dinamico de la URL.Siempre debe ir despues que los enlaces que contengan /id/*
  { path: 'usuarios/:id', component: DetalleUsuario },


];
