package com.fernandoruiz87.forohub.domain.curso.entity;

import com.fernandoruiz87.forohub.domain.curso.CategoriaCurso;
import com.fernandoruiz87.forohub.domain.curso.dto.ActualizarCurso;
import com.fernandoruiz87.forohub.domain.curso.dto.RegistroCurso;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "curso")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private CategoriaCurso categoria;

    public Curso(RegistroCurso datos) {
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }

    public void actualizarDatos(ActualizarCurso datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.categoria() != null) {
            this.categoria = datos.categoria();
        }
    }
}
