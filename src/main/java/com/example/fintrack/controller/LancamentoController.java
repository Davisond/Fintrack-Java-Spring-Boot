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
import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.LancamentoNaoEncontradoException;


@RestController
@RequestMapping("/api/lancamentos")

public class LancamentoController {

    private final LancamentoServico servico;

    public LancamentoController(LancamentoServico servico){
        this.servico = servico;
    }

    @GetMapping 
    public List<Lancamento> listar(){
        return servico.listar();
    }
    
    @GetMapping("/{id}")
    public Lancamento buscar(@PathVariable UUID id) {
        return servico.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@RequestBody Lancamento dados) {
        Lancamento criado = servico.criar(dados);
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(local).body(criado);
    }

    @PutMapping("/{id}")
    public Lancamento atualizar(@PathVariable UUID id, @RequestBody Lancamento dados) {
        return servico.atualizar(id, dados);
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
