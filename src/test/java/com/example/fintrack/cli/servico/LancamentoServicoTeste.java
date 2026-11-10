package com.example.fintrack.cli.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.fintrack.Servico.LancamentoServico;
import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.LancamentoNaoEncontradoException;
import com.example.fintrack.modelo.TipoLancamento;
import com.example.fintrack.repositorio.RepositorioEmMemoria;
import com.example.fintrack.repositorio.RepositorioLancamentos;

  
public class LancamentoServicoTeste {

    private final RepositorioLancamentos repositorio = new RepositorioEmMemoria();
    private final LancamentoServico servico = new LancamentoServico(repositorio);

    @Test
    void buscarLancamentoExistente() {
        Lancamento l = new Lancamento(UUID.randomUUID(), "Salario",
            new BigDecimal("3000.00"), LocalDate.of(2026, 10, 5), TipoLancamento.RECEITA);
        repositorio.salvar(l);

        assertEquals(l, servico.buscar(l.id()));
    }

    @Test
    void lancaExcecaoQuandoNaoExiste() {
        assertThrows(LancamentoNaoEncontradoException.class,
                () -> servico.buscar(UUID.randomUUID()));
    }

    @Test
    void removerDevolveFalseQuandoNaoExiste() {
        assertFalse(repositorio.remover(UUID.randomUUID()));
    }
 
    @Test 
     void somaApenasDespesasDoMesInformado() {
        servico.salvar(new Lancamento(UUID.randomUUID(), "Mercado",
                new BigDecimal("100.00"), LocalDate.of(2026, 10, 3), TipoLancamento.DESPESA));
        servico.salvar(new Lancamento(UUID.randomUUID(), "Internet",
                new BigDecimal("50.50"), LocalDate.of(2026, 10, 20), TipoLancamento.DESPESA));
        servico.salvar(new Lancamento(UUID.randomUUID(), "Salário",
                new BigDecimal("3000.00"), LocalDate.of(2026, 10, 5), TipoLancamento.RECEITA));
        servico.salvar(new Lancamento(UUID.randomUUID(), "Cinema",
                new BigDecimal("40.00"), LocalDate.of(2026, 9, 28), TipoLancamento.DESPESA));

        assertEquals(new BigDecimal("150.50"),
                servico.totalDespesasDoMes(YearMonth.of(2026, 10)));
    }
        @Test
        void devolveAMaiorDespesa() {
                servico.salvar(new Lancamento(UUID.randomUUID(), "Mercado",
                        new BigDecimal("100.00"), LocalDate.of(2026, 10, 3), TipoLancamento.DESPESA));
                Lancamento aluguel = servico.salvar(new Lancamento(UUID.randomUUID(), "Aluguel",
                        new BigDecimal("1200.00"), LocalDate.of(2026, 10, 5), TipoLancamento.DESPESA));
                servico.salvar(new Lancamento(UUID.randomUUID(), "Salário",
                        new BigDecimal("5000.00"), LocalDate.of(2026, 10, 5), TipoLancamento.RECEITA));

                assertEquals(aluguel, servico.maiorDespesa().orElseThrow());
        }
        @Test
        void maiorDespesaVaziaQuandoNaoHaDespesas() {
                assertTrue(servico.maiorDespesa().isEmpty());
        }

        @Test
        void agrupaLancamentosPorTipo() {
                servico.salvar(new Lancamento(UUID.randomUUID(), "Mercado",
                        new BigDecimal("100.00"), LocalDate.of(2026, 10, 3), TipoLancamento.DESPESA));
                servico.salvar(new Lancamento(UUID.randomUUID(), "Internet",
                        new BigDecimal("50.50"), LocalDate.of(2026, 10, 20), TipoLancamento.DESPESA));
                servico.salvar(new Lancamento(UUID.randomUUID(), "Salário",
                        new BigDecimal("3000.00"), LocalDate.of(2026, 10, 5), TipoLancamento.RECEITA));

                var grupos = servico.agruparPorTipo();

                assertEquals(2, grupos.get(TipoLancamento.DESPESA).size());
                assertEquals(1, grupos.get(TipoLancamento.RECEITA).size());
        }

        @Test
        void devolveOsMaisRecentesEmOrdemDecrescente() {
        Lancamento antigo = servico.salvar(new Lancamento(UUID.randomUUID(), "Antigo",
                new BigDecimal("10.00"), LocalDate.of(2026, 9, 1), TipoLancamento.DESPESA));
        Lancamento meio = servico.salvar(new Lancamento(UUID.randomUUID(), "Meio",
                new BigDecimal("20.00"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA));
        Lancamento novo = servico.salvar(new Lancamento(UUID.randomUUID(), "Novo",
                new BigDecimal("30.00"), LocalDate.of(2026, 11, 1), TipoLancamento.DESPESA));

        assertEquals(List.of(novo, meio), servico.maisRecentes(2));
        }

        @Test
        void maisRecentesDevolveTodosQuandoHaMenosQueOPedido() {
        servico.salvar(new Lancamento(UUID.randomUUID(), "Único",
                new BigDecimal("10.00"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA));

        assertEquals(1, servico.maisRecentes(5).size());
        }

        @Test
        void removeLancamentoExistente() {
        Lancamento l = servico.salvar(new Lancamento(UUID.randomUUID(), "Mercado",
                new BigDecimal("100.00"), LocalDate.of(2026, 10, 3), TipoLancamento.DESPESA));

        servico.remover(l.id());

        assertThrows(LancamentoNaoEncontradoException.class, () -> servico.buscar(l.id()));
        }

        @Test
        void removerInexistenteLancaExcecao() {
        assertThrows(LancamentoNaoEncontradoException.class,
                () -> servico.remover(UUID.randomUUID()));
        }












}
