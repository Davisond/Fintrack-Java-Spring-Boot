package com.example.fintrack.cli.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Lancamento(
        UUID id,
        String descricao,
        BigDecimal valor,
        LocalDate data,
        TipoLancamento tipo) {

    public Lancamento {
        List<String> erros = validar(descricao, valor, data, tipo);
        if (!erros.isEmpty()) {
            throw new IllegalArgumentException(String.join("; ", erros));
        }
    }

    public static Resultado<Lancamento> criar(
            String descricao, BigDecimal valor, LocalDate data, TipoLancamento tipo) {
        List<String> erros = validar(descricao, valor, data, tipo);
        if (!erros.isEmpty()) {
            return Resultado.falha(erros);
        }
        return Resultado.ok(new Lancamento(UUID.randomUUID(), descricao, valor, data, tipo));
    }

    private static List<String> validar(
            String descricao, BigDecimal valor, LocalDate data, TipoLancamento tipo) {
        List<String> erros = new ArrayList<>();
        if (descricao == null || descricao.isBlank()) {
            erros.add("Descrição não pode ser vazia");
        }
        if (valor == null || valor.signum() <= 0) {
            erros.add("Valor deve ser maior que zero");
        }
        if (data == null) {
            erros.add("Data é obrigatória");
        }
        if (tipo == null) {
            erros.add("Tipo é obrigatório");
        }
        return erros;
    }
}
