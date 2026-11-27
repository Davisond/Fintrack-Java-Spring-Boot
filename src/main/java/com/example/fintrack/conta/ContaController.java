package com.example.fintrack.conta;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Contas", description = "Cadastro de contas")
@RestController
@RequestMapping("/api/v1/contas")
public class ContaController {

    private final ContaServico servico;

    public ContaController(ContaServico servico) {
        this.servico = servico;
    }

    @GetMapping
    public List<ContaResponse> listar() {
        return servico.listar().stream()
                .map(ContaResponse::de)
                .toList();
    }

    @GetMapping("/{id}")
    public ContaResponse buscar(@PathVariable UUID id) {
        return ContaResponse.de(servico.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody ContaRequest request) {
        ContaResponse criada = ContaResponse.de(servico.criar(request.nome(), request.saldoInicial()));
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criada.id())
                .toUri();
        return ResponseEntity.created(local).body(criada);
    }

    @PutMapping("/{id}")
    public ContaResponse atualizar(@PathVariable UUID id, @Valid @RequestBody ContaRequest request) {
        return ContaResponse.de(servico.atualizar(id, request.nome(), request.saldoInicial()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}