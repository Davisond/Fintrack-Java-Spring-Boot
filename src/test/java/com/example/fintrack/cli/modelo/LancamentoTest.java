package com.example.fintrack.cli.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LancamentoTest {

     @Test
    void criaLancamentoValido() {
        Resultado<Lancamento> r = Lancamento.criar(
                "Mercado", new BigDecimal("150.90"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);

        assertTrue(r.ehValido());
        assertEquals("Mercado", r.valor().descricao());
    }

    @Test
    void devolveTodosOsErrosDeUmaVez() {
        Resultado<Lancamento> r = Lancamento.criar("  ", BigDecimal.ZERO, null, null);

        assertFalse(r.ehValido());
        assertEquals(4, r.erros().size());
    }
}
