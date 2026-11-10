package com.example.fintrack.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.fintrack.modelo.Lancamento;

public interface RepositorioLancamentos {

    Lancamento salvar(Lancamento lancamento);

    Optional<Lancamento> buscarPorId(UUID id);

    List<Lancamento> listar();

    boolean remover(UUID id);
    

    
    

}
