// Refleja la estructura que devuelve el backend cuando una lista viene paginada.
// T permite reutilizar el mismo contrato para usuarios, posts u otros DTOs futuros.
export interface PageResponse<T> {
  // Elementos de la pagina actual.
  content: T[];

  // Total real de elementos despues de aplicar filtros.
  totalElements: number;

  // Numero de paginas disponibles con el size actual.
  totalPages: number;

  // Pagina actual. Spring Data usa indice base 0.
  page: number;

  // Cantidad solicitada por pagina.
  size: number;
}
