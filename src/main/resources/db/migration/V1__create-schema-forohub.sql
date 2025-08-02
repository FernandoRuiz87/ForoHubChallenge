
-- Tabla Usuario
CREATE TABLE usuario
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    nombre             VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL UNIQUE,
    contrasena         VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabla Curso
CREATE TABLE curso
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(100) NOT NULL,
    categoria VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id)
);

-- Tabla Tópico
CREATE TABLE topico
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    titulo         VARCHAR(200) NOT NULL,
    mensaje        TEXT         NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status         VARCHAR(30)  NOT NULL,
    autor_id       BIGINT       NOT NULL,
    curso_id       BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuario (id),
    FOREIGN KEY (curso_id) REFERENCES curso (id)
);

-- Tabla Respuesta
CREATE TABLE respuesta
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    mensaje        TEXT   NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    solucion       BOOLEAN   DEFAULT FALSE,
    topico_id      BIGINT NOT NULL,
    autor_id       BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topico (id),
    FOREIGN KEY (autor_id) REFERENCES usuario (id)
);
