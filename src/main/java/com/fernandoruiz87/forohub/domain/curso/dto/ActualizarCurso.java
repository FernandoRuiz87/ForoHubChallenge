package com.fernandoruiz87.forohub.domain.curso.dto;

import com.fernandoruiz87.forohub.domain.curso.CategoriaCurso;
import jakarta.validation.constraints.NotNull;

public record ActualizarCurso(
        @NotNull
        Long id,
        String nombre,
        CategoriaCurso categoria
) {
}
