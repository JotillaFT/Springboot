import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Usuarios } from './pages/usuarios/usuarios';
import { CrearUsuario } from './pages/crear-usuario/crear-usuario';

export const routes: Routes = [
  {path: '', component: Home},
  {path: 'usuarios',component: Usuarios},
  {path:'usuarios/nuevo', component: CrearUsuario}
];
