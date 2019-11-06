package tcc.PluriGame.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "professores")
public class Professores {
	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//referencia da coluna no json - nao por letra maiuscula
		
		@Column(name = "nome")
		private String nome;
		
		@Column(name = "grandearea")
		private String grandearea;
		
		@ManyToOne
		@JoinColumn(name = "idusuario")
		private Usuarios idusuario;

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

		public String getGrandearea() {
			return grandearea;
		}

		public void setGrandearea(String grandearea) {
			this.grandearea = grandearea;
		}

		public Usuarios getIdusuario() {
			return idusuario;
		}

		public void setIdusuario(Usuarios idusuario) {
			this.idusuario = idusuario;
		}


}
