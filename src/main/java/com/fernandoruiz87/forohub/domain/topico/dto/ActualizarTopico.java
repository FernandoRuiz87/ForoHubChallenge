package com.fernandoruiz87.forohub.domain.topico.dto;

import java.time.LocalDateTime;

public record ActualizarTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status
) {
}
