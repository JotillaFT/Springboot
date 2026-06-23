import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Usuario } from './model/usuario';
import { UsuarioService } from './services/usuario.service';

@Component({
  selector: 'app-root',
  imports:[RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
}
