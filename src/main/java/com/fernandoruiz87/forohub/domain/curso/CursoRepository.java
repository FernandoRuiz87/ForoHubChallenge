package com.fernandoruiz87.forohub.domain.curso;

import com.fernandoruiz87.forohub.domain.curso.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso,Long> {
}
