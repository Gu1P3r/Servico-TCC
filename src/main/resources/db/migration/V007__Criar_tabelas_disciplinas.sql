CREATE TABLE disciplinas(
	id				SERIAL PRIMARY KEY,
	nome			VARCHAR(100)	NOT NULL,
	idcurso			INTEGER			NOT NULL,
	idprofessor		INTEGER			NOT NULL,
	FOREIGN KEY (idcurso) REFERENCES cursos(id),
	FOREIGN KEY (idprofessor) REFERENCES professores(id)
);