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

import tcc.PluriGame.domain.model.Professores;
import tcc.PluriGame.domain.repository.ProfessoresRepository;


@RestController
@RequestMapping("/professores") // rota de acesso
public class ProfessoresResource {
	
	@Autowired
	private ProfessoresRepository professoresRepository;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN', 'PROF')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Professores> addProfessores(@Valid @RequestBody Professores professores) {
		Professores professoresnew = professoresRepository.save(professores);
		return ResponseEntity.status(HttpStatus.CREATED).body(professoresnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Professores>> getProfessores() {
		List<Professores> getAllProfessores = professoresRepository.findAll();
		if (getAllProfessores.isEmpty()) {
			return new ResponseEntity<List<Professores>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Professores>>(getAllProfessores, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN', 'PROF')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Professores> getProfessor(@PathVariable("id") Long id) {
		Professores getProfessores = professoresRepository.findOne(id);
		if (getProfessores == null) {
			System.out.println("Professor com id " + id + "não encontrado");
			return new ResponseEntity<Professores>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Professores>(getProfessores, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Professores> updateProfessores(@PathVariable("id") Long id, @Valid @RequestBody Professores professores) {
		System.out.println("Procurando Professor de id " + id);

		Professores professoresAtual = professoresRepository.findOne(id);

		if (professoresAtual == null) {
			System.out.println("Professor com id " + id + " não encontrado");
			return new ResponseEntity<Professores>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(professores, professoresAtual, "id"); // observacao
		professoresAtual.setNome(professoresAtual.getNome());
		professoresAtual.setGrandearea(professoresAtual.getGrandearea());
		professoresAtual.setIdusuario(professoresAtual.getIdusuario());
		
		professoresRepository.save(professores);
		return new ResponseEntity<Professores>(professores, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Professores> deleteProfessores(@PathVariable("id") long id) {
		System.out.println("Procurando Professor de id " + id);

		Professores deletarProfessores = professoresRepository.findOne(id);
		if (deletarProfessores == null) {
			System.out.println("Não foi possivel deletar professor com id: " + id + ", id não encontrado");
			return new ResponseEntity<Professores>(HttpStatus.NOT_FOUND);
		}
		professoresRepository.delete(id);
		return new ResponseEntity<Professores>(HttpStatus.NO_CONTENT);
	}

}
