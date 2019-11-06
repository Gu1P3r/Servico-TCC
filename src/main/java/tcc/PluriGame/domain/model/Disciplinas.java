package tcc.PluriGame.domain.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "disciplinas")
public class Disciplinas {

		@Id	
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private long id;
		
		//referencia da coluna no json - nao por letra maiuscula
		
			@Column(name = "nome") 
			private String nome;
	
			
			@ManyToOne
			@JoinColumn(name = "idcurso")
			private Cursos idcurso;

			@ManyToOne
			@JoinColumn(name = "idprofessor")
			private Professores idprofessor;


			public long getId() {
				return id;
			}


			public void setId(long id) {
				this.id = id;
			}


			public String getNome() {
				return nome;
			}


			public void setNome(String nome) {
				this.nome = nome;
			}
			
			public Cursos getIdcurso() {
				return idcurso;
			}


			public void setIdcurso(Cursos idcurso) {
				this.idcurso = idcurso;
			}


			public Professores getIdprofessor() {
				return idprofessor;
			}


			public void setIdprofessor(Professores idprofessor) {
				this.idprofessor = idprofessor;
			}
	
	
}
