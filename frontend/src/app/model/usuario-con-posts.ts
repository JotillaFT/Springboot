import { PostSimple } from './post-simple';

// Modelo usado en el detalle de usuario.
// Incluye los datos basicos del usuario y una lista reducida de sus posts.
export interface UsuarioConPosts {
  id: number;
  nombre: string;
  edad: number;

  // Se usa PostSimple porque en el perfil solo hace falta id y titulo para listar/enlazar posts.
  posts: PostSimple[];
}
