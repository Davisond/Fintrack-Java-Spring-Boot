package com.example.fintrack.categoria;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoriaServico {

    private final CategoriaRepository repositorio;

    public CategoriaServico(CategoriaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Categoria> listar() {
        return repositorio.findAll(Sort.by("nome"));
    }

    public Categoria buscar(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    @Transactional
    public Categoria criar(String nome, String cor) {
        return repositorio.save(new Categoria(nome, cor));
    }

    @Transactional
    public Categoria atualizar(UUID id, String nome, String cor) {
        Categoria categoria = buscar(id);
        categoria.atualizar(nome, cor);
        return categoria;
    }

    @Transactional
    public void remover(UUID id) {
        repositorio.delete(buscar(id));
    }
}