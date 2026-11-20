package com.example.fintrack.repositorio.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.repositorio.RepositorioLancamentos;

@Repository
public class RepositorioJpa implements RepositorioLancamentos {

    private final LancamentoJpaRepository jpa;

    public RepositorioJpa(LancamentoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return paraDominio(jpa.save(paraEntidade(lancamento)));
    }

    @Override
    public Optional<Lancamento> buscarPorId(UUID id) {
        return jpa.findById(id).map(RepositorioJpa::paraDominio);
    }

    @Override
    public List<Lancamento> listar() {
        return jpa.findAll().stream()
                .map(RepositorioJpa::paraDominio)
                .toList();
    }

    @Override
    public boolean remover(UUID id) {
        if (!jpa.existsById(id)) {
            return false;
        }
        jpa.deleteById(id);
        return true;
    }

    private static LancamentoEntity paraEntidade(Lancamento l) {
        return new LancamentoEntity(l.id(), l.descricao(), l.valor(), l.data(), l.tipo());
    }

    private static Lancamento paraDominio(LancamentoEntity e) {
        return new Lancamento(e.getId(), e.getDescricao(), e.getValor(), e.getData(), e.getTipo());
    }
}