CREATE TABLE alunos_disciplinas(
	idaluno			INTEGER NOT NULL,
	iddisciplina	INTEGER NOT NULL,
	FOREIGN KEY (idaluno) REFERENCES Alunos(id),
	FOREIGN KEY (iddisciplina) REFERENCES Disciplinas(id),
	PRIMARY KEY (idaluno, iddisciplina)
);