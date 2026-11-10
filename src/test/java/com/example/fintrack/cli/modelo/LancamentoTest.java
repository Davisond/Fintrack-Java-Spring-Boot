package com.example.fintrack.cli.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.Resultado;
import com.example.fintrack.modelo.TipoLancamento;

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



        @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-0.01"})
    void rejeitaValorNaoPositivo(String valor) {
        Resultado<Lancamento> r = Lancamento.criar(
                "Teste", new BigDecimal(valor), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);

        assertFalse(r.ehValido());
        assertEquals(List.of("Valor deve ser maior que zero"), r.erros());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void rejeitaDescricaoVazia(String descricao) {
        Resultado<Lancamento> r = Lancamento.criar(
                descricao, new BigDecimal("10.00"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);

        assertFalse(r.ehValido());
        assertEquals(List.of("Descrição não pode ser vazia"), r.erros());
    }
}
