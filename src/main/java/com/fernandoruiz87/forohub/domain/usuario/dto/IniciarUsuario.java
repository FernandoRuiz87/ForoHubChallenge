package com.fernandoruiz87.forohub.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record IniciarUsuario(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String contrasena
) {
}
