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

import tcc.PluriGame.domain.model.Cursos;
import tcc.PluriGame.domain.repository.CursosRepository;


@RestController
@RequestMapping("/cursos") // rota de acesso
public class CursosResource {
	
	@Autowired
	private CursosRepository cursosRepository;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Cursos> addCursos(@Valid @RequestBody Cursos cursos) {
		Cursos cursosnew = cursosRepository.save(cursos);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursosnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Cursos>> getCursos() {
		List<Cursos> getAllCursos = cursosRepository.findAll();
		if (getAllCursos.isEmpty()) {
			return new ResponseEntity<List<Cursos>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Cursos>>(getAllCursos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Cursos> getCurso(@PathVariable("id") Long id) {
		Cursos getCursos = cursosRepository.findOne(id);
		if (getCursos == null) {
			System.out.println("Curso com id " + id + "não encontrado");
			return new ResponseEntity<Cursos>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cursos>(getCursos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Cursos> updateCursos(@PathVariable("id") Long id, @Valid @RequestBody Cursos cursos) {
		System.out.println("Procurando Curso de id " + id);

		Cursos cursosAtual = cursosRepository.findOne(id);

		if (cursosAtual == null) {
			System.out.println("Curso com id " + id + " não encontrado");
			return new ResponseEntity<Cursos>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(cursos, cursosAtual, "id"); // observacao
		cursosAtual.setNome(cursosAtual.getNome());
		
		cursosRepository.save(cursos);
		return new ResponseEntity<Cursos>(cursos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Cursos> deleteCursos(@PathVariable("id") long id) {
		System.out.println("Procurando Curso de id " + id);

		Cursos deletarCursos = cursosRepository.findOne(id);
		if (deletarCursos == null) {
			System.out.println("Não foi possivel deletar curso com id: " + id + ", id não encontrado");
			return new ResponseEntity<Cursos>(HttpStatus.NOT_FOUND);
		}
		cursosRepository.delete(id);
		return new ResponseEntity<Cursos>(HttpStatus.NO_CONTENT);
	}

}
