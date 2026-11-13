package com.example.fintrack.mapper;

import java.util.UUID;

import com.example.fintrack.dto.LancamentoRequest;
import com.example.fintrack.dto.LancamentoResponse;
import com.example.fintrack.modelo.Lancamento;

public class LancamentoMapper {

    public Lancamento toDominio(LancamentoRequest request){
        return new Lancamento(UUID.randomUUID(), request.descricao(),
        request.valor(), request.data(), request.tipo());
    }

    public LancamentoResponse toResponse(Lancamento lancamento) {
        return new LancamentoResponse(lancamento.id(), lancamento.descricao(),
                lancamento.valor(), lancamento.data(), lancamento.tipo());
    }

}
