ALTER TABLE usuario
    CHANGE correo_electronico email VARCHAR(150) NOT NULL UNIQUE;