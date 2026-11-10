package com.example.fintrack.modelo;

import java.util.List;

public record Resultado<T>(T valor, List<String> erros) {
    
    public Resultado {
        erros = List.copyOf(erros);
    }

    public static <T> Resultado<T> ok(T valor) {
        return new Resultado<>(valor, List.of());
    }

    public static <T> Resultado<T> falha(List<String> erros) {
        return new Resultado<>(null, erros);
    }

    public boolean ehValido(){
        return erros.isEmpty();
    }

}
