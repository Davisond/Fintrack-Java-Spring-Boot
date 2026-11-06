package com.example.fintrack.cli.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record Lancamento(
    UUID id,
    String descricao,
    BigDecimal valor,
    LocalDate data,
    TipoLancamento tipo) {

        public Lancamento {
            if (descricao == null || descricao.isBlank()){
                throw new IllegalArgumentException("Descricao nao pode ser vazio");

            } 
            if (valor == null || valor.signum() <= 0){
                throw new IllegalArgumentException("Valor precisa ser maior que zero");
            }

        }

}
