CREATE TABLE alunos(
	id			SERIAL PRIMARY KEY,
	raaluno			VARCHAR(15)		NOT NULL,
	nome		VARCHAR(100)	NOT NULL,
	acertos		INTEGER,
	idusuario		INTEGER NOT NULL,
	idcurso			INTEGER NOT NULL,
	FOREIGN KEY (idusuario) REFERENCES usuarios(id),
	FOREIGN KEY (idcurso) REFERENCES cursos(id)
);