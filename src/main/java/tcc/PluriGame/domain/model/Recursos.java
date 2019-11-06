package tcc.PluriGame.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "recursos")
public class Recursos {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	//referencia da coluna no json - nao por letra maiuscula
	
			@Column(name = "nome") 
			private String nome;
	
			@Column(name = "tipoprova") 
			private String tipoprova;
			
			@Column(name = "professormateria") 
			private String professormateria;
			
			@Column(name = "numeroquestao") 
			private int numeroquestao;
			
			@Column(name = "fundamento") 
			private String fundamento;
			
			@Column(name = "curso") 
			private String curso;
			
			@Column(name = "solicitacao") 
			private String solicitacao;
			
			@Column(name = "data")
			@JsonFormat(pattern = "yyyy-MM-dd")
			private LocalDate data;
			
			@ManyToOne
			@JoinColumn(name = "idaluno") 
			private Alunos idaluno;

			@Override
			public String toString() {
				return "Dados do Recurso = ( Nome do Aluno:" + nome + ", Tipo da Prova:" + tipoprova + ", Professor da Matéria:"
						+ professormateria + ", Número da Questão:" + numeroquestao + ", Curso:" + curso + ", Solicitação:" + solicitacao + ", Data do Recurso:" + data + ", Fundamento=" + fundamento
						+ ")";
			}

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

			public String getTipoprova() {
				return tipoprova;
			}

			public void setTipoprova(String tipoprova) {
				this.tipoprova = tipoprova;
			}

			public String getProfessormateria() {
				return professormateria;
			}

			public void setProfessormateria(String professormateria) {
				this.professormateria = professormateria;
			}

			public int getNumeroquestao() {
				return numeroquestao;
			}

			public void setNumeroquestao(int numeroquestao) {
				this.numeroquestao = numeroquestao;
			}

			public String getFundamento() {
				return fundamento;
			}

			public void setFundamento(String fundamento) {
				this.fundamento = fundamento;
			}

			public String getCurso() {
				return curso;
			}

			public void setCurso(String curso) {
				this.curso = curso;
			}

			public String getSolicitacao() {
				return solicitacao;
			}

			public void setSolicitacao(String solicitacao) {
				this.solicitacao = solicitacao;
			}

			public LocalDate getData() {
				return data;
			}

			public void setData(LocalDate data) {
				this.data = data;
			}

			public Alunos getIdaluno() {
				return idaluno;
			}

			public void setIdaluno(Alunos idaluno) {
				this.idaluno = idaluno;
			}
			
			
			
			
}
