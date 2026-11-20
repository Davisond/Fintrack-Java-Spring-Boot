package com.example.fintrack.repositorio.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface LancamentoJpaRepository extends JpaRepository<LancamentoEntity, UUID> {

}
