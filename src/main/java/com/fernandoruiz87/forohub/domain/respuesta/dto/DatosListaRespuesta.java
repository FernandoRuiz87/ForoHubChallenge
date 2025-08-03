package com.fernandoruiz87.forohub.domain.respuesta.dto;

public record DatosListaRespuesta(
        Long id,
        String mensaje,
        String fechaCreacion,
        String solucion,
        Long autor_id
) {
}
