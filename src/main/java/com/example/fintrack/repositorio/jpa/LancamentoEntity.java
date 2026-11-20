package com.example.fintrack.repositorio.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.example.fintrack.modelo.TipoLancamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "lancamento")
public class LancamentoEntity {

@Id
private UUID id;

@Column(nullable = false, length = 120)
private String descricao;

@Column(nullable = false, precision = 15, scale = 2)
private BigDecimal valor;

@Column(nullable = false)
private LocalDate data;

@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 20)
private TipoLancamento tipo;

protected LancamentoEntity() {
}
    
public LancamentoEntity(UUID id, String descricao, BigDecimal valor, LocalDate data, TipoLancamento tipo) {
this.id = id;
this.descricao = descricao;
this.valor = valor;
this.data = data;
this.tipo = tipo;
}

public UUID getId() { return id; }

public String getDescricao() { return descricao; }

public BigDecimal getValor() { return valor; }

public LocalDate getData() { return data; }

public TipoLancamento getTipo() { return tipo; }
}






