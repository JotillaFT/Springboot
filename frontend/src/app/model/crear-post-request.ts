// Cuerpo JSON que Angular envia al crear un post.
// El usuario no va aqui porque se toma de la ruta /usuarios/:id/posts/nuevo.
export interface CrearPostRequest {
  titulo: string;
  contenido: string;
}
