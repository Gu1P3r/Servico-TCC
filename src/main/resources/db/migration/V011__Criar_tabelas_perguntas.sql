CREATE TABLE perguntas(
	id						SERIAL PRIMARY KEY,
	questao					VARCHAR(500)	NOT NULL,
	alternativa1			VARCHAR(100)	NOT NULL,
	alternativa2			VARCHAR(100)	NOT NULL,
	alternativa3			VARCHAR(100)	NOT NULL,
	alternativa4			VARCHAR(100)	NOT NULL,
	correta					VARCHAR(500)	NOT NULL,
	nivel					INTEGER			NOT NULL,
	assunto					VARCHAR(500)	NOT NULL,
	reaplicacao				INTEGER			NOT NULL,
	anoaplicado				INTEGER,
	iddisciplina			INTEGER			NOT NULL,
	idprofessor				INTEGER			NOT NULL,
	idcurso					INTEGER			NOT NULL,
	FOREIGN KEY (idprofessor) REFERENCES professores(id),
	FOREIGN KEY (iddisciplina) REFERENCES disciplinas(id),
	FOREIGN KEY (idcurso) REFERENCES cursos(id)
);