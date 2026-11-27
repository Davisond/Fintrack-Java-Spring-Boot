package com.example.fintrack.conta;

import java.util.UUID;

public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException(UUID id) {
        super("Conta não encontrada: " + id);
    }
}