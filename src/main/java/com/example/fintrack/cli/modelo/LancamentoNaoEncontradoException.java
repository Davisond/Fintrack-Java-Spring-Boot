package com.example.fintrack.cli.modelo;

import java.util.UUID;

public class LancamentoNaoEncontradoException extends RuntimeException {

    public LancamentoNaoEncontradoException(UUID id){
        super("Lancamento não encontrado: " + id);
    }
    



}
