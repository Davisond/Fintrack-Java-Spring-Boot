package com.example.fintrack.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.fintrack.Servico.LancamentoServico;
import com.example.fintrack.dto.LancamentoRequest;
import com.example.fintrack.dto.LancamentoResponse;
import com.example.fintrack.mapper.LancamentoMapper;
import com.example.fintrack.modelo.LancamentoNaoEncontradoException;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final LancamentoServico servico;
    private final LancamentoMapper mapper;

    public LancamentoController(LancamentoServico servico, LancamentoMapper mapper) {
        this.servico = servico;
        this.mapper = mapper;
    }

    @GetMapping
    public List<LancamentoResponse> listar() {
        return servico.listar().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public LancamentoResponse buscar(@PathVariable UUID id) {
        return mapper.toResponse(servico.buscar(id));
    }

    @PostMapping
    public ResponseEntity<LancamentoResponse> criar(@RequestBody LancamentoRequest request) {
        LancamentoResponse criado = mapper.toResponse(servico.criar(mapper.toDominio(request)));
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(local).body(criado);
    }

    @PutMapping("/{id}")
    public LancamentoResponse atualizar(@PathVariable UUID id, @RequestBody LancamentoRequest request) {
        return mapper.toResponse(servico.atualizar(id, mapper.toDominio(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(LancamentoNaoEncontradoException.class)
    public ResponseEntity<Void> naoEncontrado() {
        return ResponseEntity.notFound().build();
    }
}
