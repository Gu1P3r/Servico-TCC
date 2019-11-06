package tcc.PluriGame.domain.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tcc.PluriGame.domain.model.Alunos;
import tcc.PluriGame.domain.model.Perguntas;
import tcc.PluriGame.domain.model.Recursos;
import tcc.PluriGame.domain.model.Usuarios;
import tcc.PluriGame.domain.repository.PerguntasRepository;
import tcc.PluriGame.domain.repository.AlunosRepository;

@RestController
@RequestMapping("/perguntas") // rota de acesso
public class PerguntasResource {
	
	@Autowired
	private PerguntasRepository perguntasRepository;
	
	@Autowired
	private AlunosRepository alunosRepository;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN', 'PROF')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Perguntas> addPerguntas(@Valid @RequestBody Perguntas perguntas) {
		Perguntas perguntasnew = perguntasRepository.save(perguntas);
		return ResponseEntity.status(HttpStatus.CREATED).body(perguntasnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Perguntas>> getPerguntas() {
		List<Perguntas> getAllPerguntas = perguntasRepository.findAll();
		if (getAllPerguntas.isEmpty()) {
			return new ResponseEntity<List<Perguntas>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Perguntas>>(getAllPerguntas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/simulado{id}")
	public ResponseEntity<List<Perguntas>> getSimulado() {
		
		List<Perguntas> getAllPerguntas = perguntasRepository.findAll();
		System.out.println(getAllPerguntas);
		Collections.shuffle(getAllPerguntas);

		if (getAllPerguntas.isEmpty()) {
			
			return new ResponseEntity<List<Perguntas>>(HttpStatus.NO_CONTENT);
		}
		
		
		return new ResponseEntity<List<Perguntas>>(getAllPerguntas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/pergunta/{id}")
	public ResponseEntity<Perguntas> getPergunta(@PathVariable("id") Long id) {
		Perguntas getPerguntas = perguntasRepository.findOne(id);
		if (getPerguntas == null) {
			System.out.println("Pergunta com id " + id + "não encontrado");
			return new ResponseEntity<Perguntas>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Perguntas>(getPerguntas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Perguntas> updatePerguntas(@PathVariable("id") Long id, @Valid @RequestBody Perguntas perguntas) {
		System.out.println("Procurando Pergunta de id " + id);

		Perguntas perguntasAtual = perguntasRepository.findOne(id);

		if (perguntasAtual == null) {
			System.out.println("Pergunta com id " + id + " não encontrado");
			return new ResponseEntity<Perguntas>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(perguntas, perguntasAtual, "id"); // observacao
		perguntasAtual.setQuestao(perguntasAtual.getQuestao());	
		perguntasAtual.setAlternativa1(perguntasAtual.getAlternativa1());
		perguntasAtual.setAlternativa2(perguntasAtual.getAlternativa2());
		perguntasAtual.setAlternativa3(perguntasAtual.getAlternativa3());
		perguntasAtual.setAlternativa4(perguntasAtual.getAlternativa4());
		perguntasAtual.setCorreta(perguntasAtual.getCorreta());
		perguntasAtual.setNivel(perguntasAtual.getNivel());
		perguntasAtual.setAssunto(perguntasAtual.getAssunto());
		perguntasAtual.setReaplicacao(perguntasAtual.getReaplicacao());
		perguntasAtual.setAnoaplicado(perguntasAtual.getAnoaplicado());
		perguntasAtual.setIddisciplina(perguntasAtual.getIddisciplina());
		perguntasAtual.setIdprofessor(perguntasAtual.getIdprofessor());
		perguntasAtual.setIdcurso(perguntasAtual.getIdcurso());
		
		perguntasRepository.save(perguntas);
		return new ResponseEntity<Perguntas>(perguntas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Perguntas> deletePerguntas(@PathVariable("id") long id) {
		System.out.println("Procurando Pergunta de id " + id);

		Perguntas deletarPerguntas = perguntasRepository.findOne(id);
		if (deletarPerguntas == null) {
			System.out.println("Não foi possivel deletar pergunta com id: " + id + ", id não encontrado");
			return new ResponseEntity<Perguntas>(HttpStatus.NOT_FOUND);
		}
		perguntasRepository.delete(id);
		return new ResponseEntity<Perguntas>(HttpStatus.NO_CONTENT);
	}

}
