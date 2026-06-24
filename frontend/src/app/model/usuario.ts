// Modelo TypeScript de un usuario tal como lo usa el frontend.
// Debe coincidir con el DTO que devuelve el backend para usuarios.
export interface Usuario{
  id : number;
  nombre : string;
  edad : number;
}
