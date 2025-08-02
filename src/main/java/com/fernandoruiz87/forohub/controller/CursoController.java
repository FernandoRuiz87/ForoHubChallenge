package com.fernandoruiz87.forohub.controller;

import com.fernandoruiz87.forohub.domain.curso.CursoRepository;
import com.fernandoruiz87.forohub.domain.curso.dto.ActualizarCurso;
import com.fernandoruiz87.forohub.domain.curso.dto.RegistroCurso;
import com.fernandoruiz87.forohub.domain.curso.dto.RespuestaCurso;
import com.fernandoruiz87.forohub.domain.curso.entity.Curso;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // Se puede cambiar a paged para mejorar la eficiencia
    @GetMapping()
    public ResponseEntity obtenerCursos() {
        var cursos = cursoRepository.findAll(); // Obtiene todos los cursos de la base de datos
        return ResponseEntity.ok(cursos);
    }

    @PostMapping()
    public ResponseEntity<RespuestaCurso> registrarCurso(
            @RequestBody @Valid RegistroCurso datos) {
        // Guarda el curso en la base de datos
        Curso curso = cursoRepository.save(new Curso(datos));

        // Crea la respuesta con los datos del curso
        RespuestaCurso respuesta = new RespuestaCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<RespuestaCurso> actualizarCurso(@RequestBody @Valid ActualizarCurso datos) {
        // Verifica si el curso existe
        Curso curso = cursoRepository.findById(datos.id())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));

        // Actualiza los datos del curso
        curso.actualizarDatos(datos);

        // Devuelve la respuesta
        return ResponseEntity.ok(new RespuestaCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        ));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarCursoa(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        // Eliminar el curso
        cursoRepository.delete(curso);
        return ResponseEntity.noContent().build();
    }
}
