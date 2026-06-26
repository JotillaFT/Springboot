// Modelo de post tal como lo consume Angular despues de llamar al backend.
// Coincide con PostResponseDTO: no trae el objeto Usuario entero, solo los datos utiles del autor.
export interface Post {
  id: number;
  titulo: string;
  contenido : string;

  // Permite navegar desde /posts/:id de vuelta al detalle del usuario propietario.
  usuarioId: number;

  // Nombre del autor ya calculado en el backend mediante PostMapper.
  nombreUsuario : string;
}
