CREATE TABLE noticias(
	id			SERIAL PRIMARY KEY,
	noticias			VARCHAR(500)	NOT NULL,
	idprofessor			INTEGER			NOT NULL,
	FOREIGN KEY (idprofessor) REFERENCES professores(id)
);