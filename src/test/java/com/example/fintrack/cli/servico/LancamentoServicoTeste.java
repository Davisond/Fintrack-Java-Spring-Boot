package com.example.fintrack.cli.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.fintrack.cli.Servico.LancamentoServico;
import com.example.fintrack.cli.modelo.Lancamento;
import com.example.fintrack.cli.modelo.LancamentoNaoEncontradoException;
import com.example.fintrack.cli.modelo.TipoLancamento;
import com.example.fintrack.cli.repositorio.RepositorioEmMemoria;
import com.example.fintrack.cli.repositorio.RepositorioLancamentos;

  
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
    
}
