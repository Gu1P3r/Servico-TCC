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

import tcc.PluriGame.domain.model.Alunos;
import tcc.PluriGame.domain.repository.AlunosRepository;


@RestController
@RequestMapping("/alunos") // rota de acesso
public class AlunosResource {
	
	@Autowired
	private AlunosRepository alunosRepository;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Alunos> addAlunos(@Valid @RequestBody Alunos alunos) {
		Alunos alunosnew = alunosRepository.save(alunos);
		return ResponseEntity.status(HttpStatus.CREATED).body(alunosnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Alunos>> getAlunos() {
		List<Alunos> getAllAlunos = alunosRepository.findAll();
		if (getAllAlunos.isEmpty()) {
			return new ResponseEntity<List<Alunos>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Alunos>>(getAllAlunos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Alunos> getAluno(@PathVariable("id") Long id) {
		Alunos getAlunos = alunosRepository.findOne(id);
		if (getAlunos == null) {
			System.out.println("Aluno com id " + id + "não encontrado");
			return new ResponseEntity<Alunos>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Alunos>(getAlunos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN')")
	@PutMapping(value = "/acertos/{id}")
	public ResponseEntity<Alunos> acertosAluno(@PathVariable("id") Long id, @Valid @RequestBody Alunos alunos) {
		System.out.println("Procurando Aluno de id " + id);

		Alunos alunosAtual = alunosRepository.findOne(id);

		if (alunosAtual == null) {
			System.out.println("Aluno com id " + id + " não encontrado");
			return new ResponseEntity<Alunos>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(alunos, alunosAtual, "id"); // observacao
		alunosAtual.setRaaluno(alunosAtual.getRaaluno());
		alunosAtual.setNome(alunosAtual.getNome());
		int acertos = alunosAtual.getAcertos() + 1;
		alunosAtual.setAcertos(acertos);
		alunosAtual.setIdusuario(alunosAtual.getIdusuario());
		alunosAtual.setIdcurso(alunosAtual.getIdcurso());
		

		alunosRepository.save(alunosAtual);
		return new ResponseEntity<Alunos>(alunosAtual, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Alunos> updateAlunos(@PathVariable("id") Long id, @Valid @RequestBody Alunos alunos) {
		System.out.println("Procurando Aluno de id " + id);

		Alunos alunosAtual = alunosRepository.findOne(id);

		if (alunosAtual == null) {
			System.out.println("Aluno com id " + id + " não encontrado");
			return new ResponseEntity<Alunos>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(alunos, alunosAtual, "id"); // observacao
		alunosAtual.setRaaluno(alunosAtual.getRaaluno());
		alunosAtual.setNome(alunosAtual.getNome());
		alunosAtual.setAcertos(alunosAtual.getAcertos());
		alunosAtual.setIdusuario(alunosAtual.getIdusuario());
		alunosAtual.setIdcurso(alunosAtual.getIdcurso());
		

		alunosRepository.save(alunos);
		return new ResponseEntity<Alunos>(alunos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Alunos> deleteAlunos(@PathVariable("id") long id) {
		System.out.println("Procurando Aluno de id " + id);

		Alunos deletarAlunos = alunosRepository.findOne(id);
		if (deletarAlunos == null) {
			System.out.println("Não foi possivel deletar aluno com id: " + id + ", id não encontrado");
			return new ResponseEntity<Alunos>(HttpStatus.NOT_FOUND);
		}
		alunosRepository.delete(id);
		return new ResponseEntity<Alunos>(HttpStatus.NO_CONTENT);
	}

}
