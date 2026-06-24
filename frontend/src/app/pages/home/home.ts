import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  // Pagina inicial con enlaces de navegacion.
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {}
