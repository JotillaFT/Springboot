import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Usuario } from './model/usuario';
import { UsuarioService } from './services/usuario.service';

@Component({
  // Componente raiz de Angular. Es el primer componente que se monta en la pagina.
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  // Este componente no tiene logica propia: solo aloja el router-outlet de app.html.
}
