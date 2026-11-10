package com.example.fintrack.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Conta {

    private final String nome;
    private BigDecimal saldo;
    private final List<Lancamento> lancamentos = new ArrayList<>();

    public Conta(String nome, BigDecimal saldoInicial){
        this.nome = nome;
        this.saldo = saldoInicial;
    }

    public void registrar(Lancamento lancamento){
        lancamentos.add(lancamento);
        if (lancamento.tipo() == TipoLancamento.RECEITA){
            saldo = saldo.add(lancamento.valor());
        } else {
            saldo = saldo.subtract(lancamento.valor());
        }

    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Lancamento> getLancamentos() {
        return List.copyOf(lancamentos);
    }

    
    




}
