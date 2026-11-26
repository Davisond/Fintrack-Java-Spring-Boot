package com.example.fintrack.categoria;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
