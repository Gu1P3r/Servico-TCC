package tcc.PluriGame.domain.resource;

import javax.validation.Valid;

import java.util.List;

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

import tcc.PluriGame.domain.model.Disciplinas;
import tcc.PluriGame.domain.repository.DisciplinasRepository;


@RestController
@RequestMapping("/disciplinas") // rota de acesso
public class DisciplinasResource {
	
	@Autowired
	private DisciplinasRepository disciplinasRepository;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN', 'PROF')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Disciplinas> addDisciplinas(@Valid @RequestBody Disciplinas disciplinas) {
		Disciplinas disciplinasnew = disciplinasRepository.save(disciplinas);
		return ResponseEntity.status(HttpStatus.CREATED).body(disciplinasnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Disciplinas>> getDisciplinas() {
		List<Disciplinas> getAllDisciplinas = disciplinasRepository.findAll();
		if (getAllDisciplinas.isEmpty()) {
			return new ResponseEntity<List<Disciplinas>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Disciplinas>>(getAllDisciplinas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Disciplinas> getDisciplina(@PathVariable("id") Long id) {
		Disciplinas getDisciplinas = disciplinasRepository.findOne(id);
		if (getDisciplinas == null) {
			System.out.println("Disciplina com id " + id + "não encontrado");
			return new ResponseEntity<Disciplinas>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Disciplinas>(getDisciplinas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Disciplinas> updateDisciplinas(@PathVariable("id") Long id, @Valid @RequestBody Disciplinas disciplinas) {
		System.out.println("Procurando Disciplina de id " + id);

		Disciplinas disciplinasAtual = disciplinasRepository.findOne(id);

		if (disciplinasAtual == null) {
			System.out.println("Disciplina com id " + id + " não encontrado");
			return new ResponseEntity<Disciplinas>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(disciplinas, disciplinasAtual, "id"); // observacao
		disciplinasAtual.setNome(disciplinasAtual.getNome());
		disciplinasAtual.setIdcurso(disciplinasAtual.getIdcurso());
		disciplinasAtual.setIdprofessor(disciplinasAtual.getIdprofessor());
		
		disciplinasRepository.save(disciplinas);
		return new ResponseEntity<Disciplinas>(disciplinas, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Disciplinas> deleteDisciplinas(@PathVariable("id") long id) {
		System.out.println("Procurando Disciplinas de id " + id);

		Disciplinas deletarProfessores = disciplinasRepository.findOne(id);
		if (deletarProfessores == null) {
			System.out.println("Não foi possivel deletar disciplina com id: " + id + ", id não encontrado");
			return new ResponseEntity<Disciplinas>(HttpStatus.NOT_FOUND);
		}
		disciplinasRepository.delete(id);
		return new ResponseEntity<Disciplinas>(HttpStatus.NO_CONTENT);
	}

}
