import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';

// Punto de entrada del frontend.
// Monta el componente raiz App usando la configuracion global definida en app.config.ts.
bootstrapApplication(App, appConfig).catch((err) => console.error(err));
