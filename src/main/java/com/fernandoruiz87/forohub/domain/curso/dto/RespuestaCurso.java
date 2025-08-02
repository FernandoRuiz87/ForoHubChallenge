package com.fernandoruiz87.forohub.domain.curso.dto;

import com.fernandoruiz87.forohub.domain.curso.CategoriaCurso;

public record RespuestaCurso(Long id, String nombre, CategoriaCurso categoria) {
}
