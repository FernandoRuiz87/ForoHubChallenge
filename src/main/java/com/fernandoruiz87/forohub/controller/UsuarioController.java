package com.fernandoruiz87.forohub.controller;

import com.fernandoruiz87.forohub.domain.usuario.UsuarioRepository;
import com.fernandoruiz87.forohub.domain.usuario.dto.IniciarUsuario;
import com.fernandoruiz87.forohub.domain.usuario.dto.RegistroUsuario;
import com.fernandoruiz87.forohub.domain.usuario.dto.RespuestaUsuario;
import com.fernandoruiz87.forohub.domain.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrarse")
    public ResponseEntity<RespuestaUsuario> post(@RequestBody @Valid RegistroUsuario datos) {
        // Verificar si ya existe un usuario con ese email
        if (usuarioRepository.existsByEmail(datos.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya existe");
        }

        // Encriptar la contraseña antes de guardarla
        var contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());
        // Crear el usuario
        Usuario usuario = new Usuario(
                datos.nombre(),
                datos.email(),
                contrasenaEncriptada
        );

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new RespuestaUsuario(usuario.getId(), usuario.getNombre(), usuario.getEmail()));
    }

    @PostMapping("/login")
    public String post(@RequestBody @Valid IniciarUsuario datos) {
        // Buscar el usuario por email
        Optional<Object> usuarioOpt = usuarioRepository.findByEmail(datos.email());

        // Si el usuario no existe, lanzar una excepción
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo electrónico o contraseña incorrectos");
        }

        Usuario usuario = (Usuario) usuarioOpt.get();

        // Verificar la contraseña
        if (!passwordEncoder.matches(datos.contrasena(), usuario.getContrasena())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo electrónico o contraseña incorrectos");
        }

        // Aquí podrías generar un token JWT o similar para autenticar al usuario
        return "Usuario autenticado con éxito: " + usuario.getNombre();
    }

}
