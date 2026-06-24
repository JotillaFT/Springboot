import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import {provideHttpClient} from '@angular/common/http';

import { routes } from './app.routes';

// Configuracion global de Angular para esta aplicacion standalone.
export const appConfig: ApplicationConfig = {
  providers: [
    // Registra listeners globales de errores del navegador.
    provideBrowserGlobalErrorListeners(),

    // Activa el sistema de rutas usando app.routes.ts.
    provideRouter(routes),

    // Permite inyectar HttpClient en servicios como UsuarioService.
    provideHttpClient()
  ]
};
