package com.example.fintrack.cli.Servico;

import java.util.UUID;

import com.example.fintrack.cli.modelo.Lancamento;
import com.example.fintrack.cli.modelo.LancamentoNaoEncontradoException;
import com.example.fintrack.cli.repositorio.RepositorioLancamentos;

public class LancamentoServico {

    private final RepositorioLancamentos repositorio;

    public LancamentoServico(RepositorioLancamentos repositorio){
        this.repositorio = repositorio;
    }

    public Lancamento buscar(UUID id){
        return repositorio.buscarPorId(id)
            .orElseThrow(() -> new LancamentoNaoEncontradoException(id));
    }


}
