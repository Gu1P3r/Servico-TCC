CREATE TABLE recursos(
	id						SERIAL 			PRIMARY KEY,
	nome		VARCHAR(100)	NOT NULL,
	tipoprova				VARCHAR(50)		NOT NULL,
	professormateria		VARCHAR(100)	NOT NULL,
	numeroquestao			INTEGER 		NOT NULL,
	fundamento				VARCHAR(700) 	NOT NULL,
	curso					VARCHAR(100)	NOT NULL,
	solicitacao				VARCHAR(100)	NOT NULL,
	data					DATE			NOT NULL,
	idaluno					INTEGER NOT NULL,
	FOREIGN KEY (idaluno) REFERENCES alunos(id)
);