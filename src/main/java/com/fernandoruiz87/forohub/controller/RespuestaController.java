package com.fernandoruiz87.forohub.controller;

import com.fernandoruiz87.forohub.domain.respuesta.RespuestaRepository;
import com.fernandoruiz87.forohub.domain.respuesta.dto.DatosListaRespuesta;
import com.fernandoruiz87.forohub.domain.respuesta.dto.DatosRespuesta;
import com.fernandoruiz87.forohub.domain.respuesta.dto.RegistroRespuesta;
import com.fernandoruiz87.forohub.domain.respuesta.entity.Respuesta;
import com.fernandoruiz87.forohub.domain.usuario.UsuarioRepository;
import com.fernandoruiz87.forohub.domain.usuario.entity.Usuario;
import com.fernandoruiz87.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ver todas las respuestas del usuario
    @GetMapping("")
    public ResponseEntity<List<DatosListaRespuesta>> obtenerRespuestasUsuario() {
        // Obtener el id del usuario desde el token
        var usuarioId = obtenerUsuarioIdDesdeToken();

        // Obtener todas las respuestas del usuario
        var respuestas = respuestaRepository.findAllByUsuarioId(usuarioId);

        // Mapear las respuestas a una lista de DatosListaRespuesta
        List<DatosListaRespuesta> datos = respuestas.stream()
                .map(r -> new DatosListaRespuesta(
                        r.getId(),
                        r.getMensaje(),
                        r.getFechaCreacion().toString(),
                        r.getSolucion(),
                        r.getUsuario().getId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(datos);
    }

    @PostMapping("")
    public ResponseEntity<DatosRespuesta> post(@RequestBody @Valid RegistroRespuesta datos) {

        System.out.println(datos);
        // Obtener el id del usuario desde el token
        Long id = obtenerUsuarioIdDesdeToken();

        var usuario = usuarioRepository.findById(id).get();

        // Crear una nueva respuesta con los datos proporcionados
        var respuesta = new Respuesta(
                null,
                datos.mensaje(),
                datos.fechaCreacion(),
                datos.solucion(),
                usuario
                );

        System.out.println(respuesta);

        // Guardar la respuesta en el repositorio
        respuestaRepository.save(respuesta);

        DatosRespuesta datosRespuesta = new DatosRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion().toString(),
                respuesta.getSolucion(),
                respuesta.getUsuario().getId()
        );
        return ResponseEntity.ok(datosRespuesta);
    }

    private Long obtenerUsuarioIdDesdeToken(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var usuario = (Usuario) auth.getPrincipal();
        System.out.println("Usuario autenticado: " + usuario.getEmail());
       return usuario.getId();
    }
}
