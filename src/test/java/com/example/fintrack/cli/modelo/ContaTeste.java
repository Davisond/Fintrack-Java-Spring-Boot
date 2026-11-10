package com.example.fintrack.cli.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.fintrack.modelo.Conta;
import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.TipoLancamento;

public class ContaTeste {

    @Test 
    void despesaDiminuiSaldo(){
        Conta conta = new Conta("corrente", new BigDecimal("1000.00"));
        conta.registrar(new Lancamento(UUID.randomUUID(), "Mercado",
         new BigDecimal("150.90"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA));

         assertEquals(new BigDecimal("849.10"), conta.getSaldo());
    }


    @Test
void valorZeroEhRejeitado() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lancamento(UUID.randomUUID(), "Teste", BigDecimal.ZERO,
                        LocalDate.now(), TipoLancamento.RECEITA));
    }
}
