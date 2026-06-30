// Version reducida de un post usada cuando solo hace falta enlazarlo/listarlo.
// No incluye contenido para no cargar texto largo dentro del detalle de usuario.
export interface PostSimple {
  id: number;
  titulo: string;
}
