package com.fernandoruiz87.forohub.domain.respuesta.entity;

import com.fernandoruiz87.forohub.domain.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
//    private String topico;
    private LocalDateTime fechaCreacion;
    private String solucion;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario usuario;


}
