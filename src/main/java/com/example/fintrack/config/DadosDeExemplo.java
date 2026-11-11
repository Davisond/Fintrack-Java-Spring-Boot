package com.example.fintrack.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.fintrack.Servico.LancamentoServico;
import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.TipoLancamento;

import jakarta.annotation.PostConstruct;


@Component
public class DadosDeExemplo {

    private final LancamentoServico servico;

    public DadosDeExemplo(LancamentoServico servico){
        this.servico = servico;
    }

    @PostConstruct 
    void carregar(){
        servico.criar(novo("Salário", "5000.00", LocalDate.of(2026, 10, 5), TipoLancamento.RECEITA));
        servico.criar(novo("Mercado", "150.90", LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA));
        servico.criar(novo("Aluguel", "1200.00", LocalDate.of(2026, 10, 10), TipoLancamento.DESPESA));
    }

    private Lancamento novo(String descricao, String valor, LocalDate data, TipoLancamento tipo){
        return new Lancamento(UUID.randomUUID(), descricao, new BigDecimal(valor), data, tipo);
    }
    





}
