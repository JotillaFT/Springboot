// Modelo del cuerpo JSON que Angular envia al crear un usuario.
// Coincide con CreateUsuarioRequest del backend.
export interface CrearUsuarioRequest{
  nombre: string;
  edad : number;
}
