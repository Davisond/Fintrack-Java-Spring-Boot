package com.example.fintrack.conta;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(name = "saldo_inicial", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoInicial;

    protected Conta() {
        // exigido pelo JPA
    }

    public Conta(String nome, BigDecimal saldoInicial) {
        this.nome = nome;
        this.saldoInicial = saldoInicial;
    }

    public void atualizar(String nome, BigDecimal saldoInicial) {
        this.nome = nome;
        this.saldoInicial = saldoInicial;
    }

    public UUID getId() { return id; }

    public String getNome() { return nome; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
}