package com.example.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.fintrack.modelo.TipoLancamento;
import com.example.fintrack.validacao.ValorMonetario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LancamentoRequest(
    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 120, message = "A descrição deve ter no máximo 120 caracteres")
    String descricao,

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    @ValorMonetario
    BigDecimal valor,

    @NotNull(message = "A data é obrigatória")
    @PastOrPresent(message = "A data não pode estar no futuro")
    LocalDate data,

    @NotNull(message = "O tipo é obrigatório")
    TipoLancamento tipo) {
}
