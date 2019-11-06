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
@Table(name = "perguntas")
public class Perguntas {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//referencia da coluna no json - nao por letra maiuscula
	
			@Column(name = "questao") 
			private String questao;

			@Column(name = "alternativa1") 
			private String alternativa1;
			
			@Column(name = "alternativa2") 
			private String alternativa2;
			
			@Column(name = "alternativa3") 
			private String alternativa3;
			
			@Column(name = "alternativa4") 
			private String alternativa4;
			
			@Column(name = "correta") 
			private String correta;
			
			@Column(name = "nivel") 
			private Integer nivel;
			
			@Column(name = "assunto") 
			private String assunto;
			
			@Column(name = "reaplicacao") 
			private Integer reaplicacao;
			
			@Column(name = "anoaplicado") 
			private Integer anoaplicado;
			
			@ManyToOne
			@JoinColumn(name = "iddisciplina")
			private Disciplinas iddisciplina;
			
			@ManyToOne
			@JoinColumn(name = "idprofessor")
			private Professores idprofessor;
			
			@ManyToOne
			@JoinColumn(name = "idcurso")
			private Cursos idcurso;

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}

			public String getQuestao() {
				return questao;
			}

			public void setQuestao(String questao) {
				this.questao = questao;
			}

			public String getAlternativa1() {
				return alternativa1;
			}

			public void setAlternativa1(String alternativa1) {
				this.alternativa1 = alternativa1;
			}

			public String getAlternativa2() {
				return alternativa2;
			}

			public void setAlternativa2(String alternativa2) {
				this.alternativa2 = alternativa2;
			}

			public String getAlternativa3() {
				return alternativa3;
			}

			public void setAlternativa3(String alternativa3) {
				this.alternativa3 = alternativa3;
			}

			public String getAlternativa4() {
				return alternativa4;
			}

			public void setAlternativa4(String alternativa4) {
				this.alternativa4 = alternativa4;
			}

			public String getCorreta() {
				return correta;
			}

			public void setCorreta(String correta) {
				this.correta = correta;
			}

			public Integer getNivel() {
				return nivel;
			}

			public void setNivel(Integer nivel) {
				this.nivel = nivel;
			}

			public String getAssunto() {
				return assunto;
			}

			public void setAssunto(String assunto) {
				this.assunto = assunto;
			}

			public Integer getReaplicacao() {
				return reaplicacao;
			}

			public void setReaplicacao(Integer reaplicacao) {
				this.reaplicacao = reaplicacao;
			}

			public Integer getAnoaplicado() {
				return anoaplicado;
			}

			public void setAnoaplicado(Integer anoaplicado) {
				this.anoaplicado = anoaplicado;
			}

			public Disciplinas getIddisciplina() {
				return iddisciplina;
			}

			public void setIddisciplina(Disciplinas iddisciplina) {
				this.iddisciplina = iddisciplina;
			}

			public Professores getIdprofessor() {
				return idprofessor;
			}

			public void setIdprofessor(Professores idprofessor) {
				this.idprofessor = idprofessor;
			}

			public Cursos getIdcurso() {
				return idcurso;
			}

			public void setIdcurso(Cursos idcurso) {
				this.idcurso = idcurso;
			}
			
}
