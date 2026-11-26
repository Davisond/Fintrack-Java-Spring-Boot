package com.example.fintrack.categoria;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, length = 7)
    private String cor;

    protected Categoria() {
        // exigido pelo JPA
    }

    public Categoria(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
    }

    public void atualizar(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
    }

    public UUID getId() { return id; }

    public String getNome() { return nome; }

    public String getCor() { return cor; }
}