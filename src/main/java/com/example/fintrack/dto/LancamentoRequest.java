package com.example.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.fintrack.modelo.TipoLancamento;

public record LancamentoRequest(
    String descricao,
    BigDecimal valor,
    LocalDate data,
    TipoLancamento tipo
) {  
}
