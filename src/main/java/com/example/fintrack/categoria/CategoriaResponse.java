package com.example.fintrack.categoria;

import java.util.UUID;

public record CategoriaResponse(UUID id, String nome, String cor) {

    public static CategoriaResponse de(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), categoria.getCor());
    }
}