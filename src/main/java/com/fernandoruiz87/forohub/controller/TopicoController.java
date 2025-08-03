package com.fernandoruiz87.forohub.controller;

import com.fernandoruiz87.forohub.domain.curso.CursoRepository;
import com.fernandoruiz87.forohub.domain.topico.TopicoRepository;
import com.fernandoruiz87.forohub.domain.topico.dto.ActualizarTopico;
import com.fernandoruiz87.forohub.domain.topico.dto.RegistroTopico;
import com.fernandoruiz87.forohub.domain.topico.dto.RespuestaTopico;
import com.fernandoruiz87.forohub.domain.topico.entity.Topico;
import com.fernandoruiz87.forohub.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<RespuestaTopico>> listarTopicos() {
        var topicos = ResponseEntity.ok(topicoRepository.findAll());
        var listaDeTopicos = topicos.getBody().stream()
                .map(t -> new RespuestaTopico(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensaje(),
                        t.getFechaCreacion(),
                        t.getStatus(),
                        t.getAutor().getNombre(),
                        t.getCurso().getNombre()
                ))
                .toList();
        return ResponseEntity.ok(listaDeTopicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopico> detalleTopico(@PathVariable String id) {
        var topico = topicoRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado"));

        return ResponseEntity.ok(new RespuestaTopico(topico));

    }

    @PostMapping("")
    public ResponseEntity<RespuestaTopico> crearTopico(@RequestBody @Valid RegistroTopico datos) {
        // Verificar si el curso y el autor existen
        var autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

        var curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        var topico = new Topico(
                null, // ID se generará automáticamente
                datos.titulo(),
                datos.mensaje(),
                datos.fechaCreacion(),
                datos.status(),
                autor,
                curso
        );

        topicoRepository.save(topico);

        return ResponseEntity.ok(new RespuestaTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaTopico> actualizarTopico(@RequestBody @Valid ActualizarTopico datos,
                                               @PathVariable Long id) {
        // Verificar si el topico existe
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topico no encontrado"));

        // Actualizar los datos del topico
        topico.actualizarDatos(datos);

        return ResponseEntity.ok(new RespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        // Eliminar el curso
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

}
