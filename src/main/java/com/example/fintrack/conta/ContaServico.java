package com.example.fintrack.conta;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ContaServico {

    private final ContaRepository repositorio;

    public ContaServico(ContaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Conta> listar() {
        return repositorio.findAll(Sort.by("nome"));
    }

    public Conta buscar(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(id));
    }

    @Transactional
    public Conta criar(String nome, BigDecimal saldoInicial) {
        return repositorio.save(new Conta(nome, saldoInicial));
    }

    @Transactional
    public Conta atualizar(UUID id, String nome, BigDecimal saldoInicial) {
        Conta conta = buscar(id);
        conta.atualizar(nome, saldoInicial);
        return conta;
    }

    @Transactional
    public void remover(UUID id) {
        repositorio.delete(buscar(id));
    }
}