import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Usuarios } from './pages/usuarios/usuarios';
import { CrearUsuario } from './pages/crear-usuario/crear-usuario';
import { DetalleUsuario } from './pages/detalle-usuario/detalle-usuario';
import { EditarUsuario } from './pages/editar-usuario/editar-usuario';
import { BorrarUsuario } from './pages/borrar-usuario/borrar-usuario';
import { CrearPost } from './pages/crear-post/crear-post';
import { DetallePost } from './pages/detalle-post/detalle-post';
import { BorrarPost } from './pages/borrar-post/borrar-post';
import { EditarPost } from './pages/editar-post/editar-post';

// Tabla de rutas de la aplicacion.
// Cada path indica que componente se debe mostrar cuando cambia la URL.
export const routes: Routes = [
  // Ruta inicial: http://localhost:4200/
  { path: '', component: Home },

  // Listado de usuarios.
  { path: 'usuarios', component: Usuarios },

  // Formulario para crear un usuario nuevo.
  { path: 'usuarios/nuevo', component: CrearUsuario },

  // Modifica un usuario o post concreto. En ambos casos :id identifica el recurso que se edita.
  { path: 'usuarios/:id/editar', component: EditarUsuario },
  { path: 'posts/:id/editar', component: EditarPost },

  // Pantallas de confirmacion de borrado. No borran al entrar: el usuario confirma dentro del componente.
  { path: 'usuarios/:id/borrar', component: BorrarUsuario },
  { path: 'posts/:id/borrar', component: BorrarPost },

  // Crea un post dentro de un usuario concreto. El :id aqui es el id del usuario propietario.
  { path: 'usuarios/:id/posts/nuevo', component: CrearPost },

  // Detalle de un post concreto. Debe ir antes que usuarios/:id para que Angular no confunda rutas.
  { path: 'posts/:id', component: DetallePost },

  // Detalle de un usuario concreto. :id es un parametro dinamico de la URL.
  // Esta ruta queda al final porque es mas generica que las rutas hijas de usuarios.
  { path: 'usuarios/:id', component: DetalleUsuario },
];
