package com.example.fintrack.repositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.example.fintrack.modelo.Lancamento;


public class RepositorioEmMemoria implements RepositorioLancamentos{
    
    private final Map<UUID, Lancamento> dados = new HashMap<>();

    @Override 
    public Lancamento salvar(Lancamento lancamento){
        dados.put(lancamento.id(), lancamento);
        return lancamento;
    }

    @Override 
    public Optional<Lancamento> buscarPorId(UUID id){
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Lancamento> listar() {
        return List.copyOf(dados.values());
    }

    @Override 
    public boolean remover(UUID id){
        return dados.remove(id) != null;
    }
}
