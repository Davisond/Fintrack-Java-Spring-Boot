package com.example.fintrack.conta;

import java.math.BigDecimal;
import java.util.UUID;

public record ContaResponse(UUID id, String nome, BigDecimal saldoInicial) {

    public static ContaResponse de(Conta conta) {
        return new ContaResponse(conta.getId(), conta.getNome(), conta.getSaldoInicial());
    }
}