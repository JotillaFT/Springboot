import { PostSimple } from './post-simple';

export interface UsuarioConPosts {
  id: number;
  nombre: string;
  edad: number;
  posts: PostSimple[];
}
