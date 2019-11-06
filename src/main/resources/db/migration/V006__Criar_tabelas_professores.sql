CREATE TABLE professores(
	id					SERIAL PRIMARY KEY,
	nome				VARCHAR(100)	NOT NULL,
	grandearea			VARCHAR(50)		NOT NULL,
	idusuario			INTEGER NOT NULL,
	FOREIGN KEY (idusuario) REFERENCES usuarios(id)
);