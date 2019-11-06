CREATE TABLE usuario_roles(
	userrolesid					SERIAL PRIMARY KEY ,
	usuario		INTEGER			NOT NULL,
	role		INTEGER			NOT NULL,
	FOREIGN KEY (usuario) REFERENCES Usuarios(id),
	FOREIGN KEY (role) REFERENCES Role(id)
);
