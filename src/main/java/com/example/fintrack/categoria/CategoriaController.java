package com.example.fintrack.categoria;

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

@Tag(name = "Categorias", description = "Cadastro de categorias de lançamentos")
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaServico servico;

    public CategoriaController(CategoriaServico servico) {
        this.servico = servico;
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return servico.listar().stream()
                .map(CategoriaResponse::de)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoriaResponse buscar(@PathVariable UUID id) {
        return CategoriaResponse.de(servico.buscar(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse criada = CategoriaResponse.de(servico.criar(request.nome(), request.cor()));
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criada.id())
                .toUri();
        return ResponseEntity.created(local).body(criada);
    }

    @PutMapping("/{id}")
    public CategoriaResponse atualizar(@PathVariable UUID id,
                                       @Valid @RequestBody CategoriaRequest request) {
        return CategoriaResponse.de(servico.atualizar(id, request.nome(), request.cor()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}