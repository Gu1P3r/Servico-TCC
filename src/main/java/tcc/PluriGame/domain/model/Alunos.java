package tcc.PluriGame.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alunos")
public class Alunos {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//referencia da coluna no json - nao por letra maiuscula
	
		@Column(name = "raaluno") 
		private String raaluno;

		@Column(name = "nome")
		private String nome;
		
		@Column(name = "acertos")
		private int acertos;

		@OneToOne
		@JoinColumn(name = "idusuario")
		private Usuarios idusuario;
		
		@ManyToOne
		@JoinColumn(name = "idcurso")
		private Cursos idcurso;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getRaaluno() {
			return raaluno;
		}

		public void setRaaluno(String raaluno) {
			this.raaluno = raaluno;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public int getAcertos() {
			return acertos;
		}

		public void setAcertos(int acertos) {
			this.acertos = acertos;
		}

		public Usuarios getIdusuario() {
			return idusuario;
		}

		public void setIdusuario(Usuarios idusuario) {
			this.idusuario = idusuario;
		}

		public Cursos getIdcurso() {
			return idcurso;
		}

		public void setIdcurso(Cursos idcurso) {
			this.idcurso = idcurso;
		}
}
