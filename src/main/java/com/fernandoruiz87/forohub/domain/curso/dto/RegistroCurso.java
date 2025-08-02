package com.fernandoruiz87.forohub.domain.curso.dto;

import com.fernandoruiz87.forohub.domain.curso.CategoriaCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroCurso(
    @NotBlank
    String nombre,
    @NotNull
    CategoriaCurso categoria
) {
}
