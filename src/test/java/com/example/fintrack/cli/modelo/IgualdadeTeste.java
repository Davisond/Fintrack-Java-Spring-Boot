package com.example.fintrack.cli.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class IgualdadeTeste {

        private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    // Classe comum SEM equals/hashCode: o Java compara pela referência
    static class DespesaSemEquals {
        private final String descricao;

        DespesaSemEquals(String descricao) {
            this.descricao = descricao;
        }
    }

    // Classe comum COM equals/hashCode escritos à mão
    static class DespesaComEquals {
        private final String descricao;

        DespesaComEquals(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DespesaComEquals outra = (DespesaComEquals) o;
            return Objects.equals(descricao, outra.descricao);
        }

        @Override
        public int hashCode() {
            return Objects.hash(descricao);
        }
    }

    private Lancamento lancamento(String descricao) {
        return new Lancamento(ID, descricao, new BigDecimal("10.00"),
                LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);
    }

    @Test
    void recordComOsMesmosCamposEhIgual() {
        Lancamento a = lancamento("Mercado");
        Lancamento b = lancamento("Mercado");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void recordComCampoDiferenteNaoEhIgual() {
        assertNotEquals(lancamento("Mercado"), lancamento("Farmácia"));
    }

    @Test
    void classeComumSemEqualsComparaPelaReferencia() {
        DespesaSemEquals a = new DespesaSemEquals("Mercado");
        DespesaSemEquals b = new DespesaSemEquals("Mercado");

        assertNotEquals(a, b);
    }

    @Test
    void classeComumPrecisaDeEqualsEHashCodeEscritosAMao() {
        DespesaComEquals a = new DespesaComEquals("Mercado");
        DespesaComEquals b = new DespesaComEquals("Mercado");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void recordTemToStringLegivelSemEscreverNada() {
        assertTrue(lancamento("Mercado").toString().contains("descricao=Mercado"));
    }
}
