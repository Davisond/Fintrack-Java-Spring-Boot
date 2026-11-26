package com.example.fintrack.categoria;
import java.util.UUID;

public class CategoriaNaoEncontradaException extends RuntimeException {

    public CategoriaNaoEncontradaException(UUID id){
        super("Categoria nao encontrada: " + id);
    }

}
