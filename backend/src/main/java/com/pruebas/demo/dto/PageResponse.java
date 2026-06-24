package com.pruebas.demo.dto;

import java.util.List;

public class PageResponse<T> {
    // Clase generica para devolver paginas de cualquier tipo de DTO.
    // T puede ser UsuarioResponseDTO, PostResponseDTO u otro DTO futuro.
    private List<T> content;

    // Metadatos de paginacion que ayudan al frontend a pintar controles de pagina.
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
