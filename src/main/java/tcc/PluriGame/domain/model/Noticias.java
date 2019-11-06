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
@Table(name = "noticias")
public class Noticias {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	//referencia da coluna no json - nao por letra maiuscula
	
	@Column(name = "noticias") 
	private String noticias	;
	
	
	@ManyToOne
	@JoinColumn(name = "idprofessor")
	private Professores idprofessor;

	
	//GET E SET - (ALT + S + R)

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNoticias() {
		return noticias;
	}

	public void setNoticias(String noticias) {
		this.noticias = noticias;
	}

	public Professores getIdprofessor() {
		return idprofessor;
	}

	public void setIdprofessor(Professores idprofessor) {
		this.idprofessor = idprofessor;
	}
	
}
