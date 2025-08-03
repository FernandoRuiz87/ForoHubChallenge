package com.fernandoruiz87.forohub.domain.respuesta;

import com.fernandoruiz87.forohub.domain.respuesta.entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    List<Respuesta> findAllByUsuarioId(Long usuarioId);
}
