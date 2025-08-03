package com.fernandoruiz87.forohub.domain.respuesta.dto;

public record DatosRespuesta(
Long id, String mensaje, String fechaCreacion, String solucion, Long autorId
) {
}
