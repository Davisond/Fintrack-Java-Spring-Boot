package com.example.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.example.fintrack.modelo.TipoLancamento;

public record LancamentoResponse(
    UUID id,
    String descricao,
    BigDecimal valor,
    LocalDate data,
    TipoLancamento tipo
) {
}
