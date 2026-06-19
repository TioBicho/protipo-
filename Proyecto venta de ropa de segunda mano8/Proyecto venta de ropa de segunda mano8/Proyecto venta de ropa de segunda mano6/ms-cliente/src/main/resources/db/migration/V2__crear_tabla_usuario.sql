CREATE TABLE usuario (
                         id_usuario       NUMBER(8)     NOT NULL,
                         email            NVARCHAR2(30) NOT NULL,
                         password         NVARCHAR2(14) NOT NULL,
                         rol              VARCHAR2(15)  NOT NULL,
                         perfil_id_perfil NUMBER(8)     NOT NULL,
                         usuario_id_usuario NUMBER(8),
                         CONSTRAINT usuario_pk PRIMARY KEY (id_usuario),
                         CONSTRAINT usuario_perfil_fk FOREIGN KEY (perfil_id_perfil)
                             REFERENCES perfil (id_perfil),
                         CONSTRAINT usuario_perfil_unique UNIQUE (perfil_id_perfil)
);