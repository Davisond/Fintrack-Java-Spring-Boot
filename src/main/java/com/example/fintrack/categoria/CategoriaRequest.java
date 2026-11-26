package com.example.fintrack.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres")
        String nome,

        @NotBlank(message = "A cor é obrigatória")
        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "A cor deve estar no formato #RRGGBB")
        String cor) {
}