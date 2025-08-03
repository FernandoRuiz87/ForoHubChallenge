package com.fernandoruiz87.forohub.domain.curso.dto;

import com.fernandoruiz87.forohub.domain.curso.CategoriaCurso;

public record ActualizarCurso(
        String nombre,
        CategoriaCurso categoria
) {
}
