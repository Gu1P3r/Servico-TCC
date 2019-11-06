package tcc.PluriGame.domain.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

import tcc.PluriGame.domain.model.Recursos;
import tcc.PluriGame.domain.repository.RecursosRepository;

@RestController
@RequestMapping("/recursos") // rota de acesso
public class RecursosResource {
	
	@Autowired
	private RecursosRepository recursoRepository;
	@Autowired
	private JavaMailSender mailSender;
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN')")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Recursos> addRecursos(@Valid @RequestBody Recursos recursos) {
		Recursos recursosnew = recursoRepository.save(recursos);
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setText(recursosnew.toString());
        message.setTo("guilherme.107.santos@gmail.com");
        message.setSubject("Recurso PLURI");
        try {
            mailSender.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar email.");
        }
		
		return ResponseEntity.status(HttpStatus.CREATED).body(recursosnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Recursos>> getRecursos() {
		List<Recursos> getAllRecursos = recursoRepository.findAll();
		if (getAllRecursos.isEmpty()) {
			return new ResponseEntity<List<Recursos>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Recursos>>(getAllRecursos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Recursos> getRecurso(@PathVariable("id") Long id) {
		Recursos getRecursos = recursoRepository.findOne(id);
		if (getRecursos == null) {
			System.out.println("Recurso com id " + id + "não encontrado");
			return new ResponseEntity<Recursos>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Recursos>(getRecursos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Recursos> updateRecursos(@PathVariable("id") Long id, @Valid @RequestBody Recursos recursos) {
		System.out.println("Procurando Recurso de id " + id);

		Recursos recursosAtual = recursoRepository.findOne(id);

		if (recursosAtual == null) {
			System.out.println("Recurso com id " + id + " não encontrado");
			return new ResponseEntity<Recursos>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(recursos, recursosAtual, "id"); // observacao
		recursosAtual.setTipoprova(recursosAtual.getTipoprova());	
		recursosAtual.setProfessormateria(recursosAtual.getProfessormateria());
		recursosAtual.setNumeroquestao(recursosAtual.getNumeroquestao());
		recursosAtual.setFundamento(recursosAtual.getFundamento());
		recursosAtual.setCurso(recursosAtual.getCurso());
		recursosAtual.setData(recursosAtual.getData());
		recursosAtual.setSolicitacao(recursosAtual.getSolicitacao());
		recursosAtual.setIdaluno(recursosAtual.getIdaluno());
		
		recursoRepository.save(recursos);
		return new ResponseEntity<Recursos>(recursos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Recursos> deleteRecursos(@PathVariable("id") long id) {
		System.out.println("Procurando Recurso de id " + id);

		Recursos deletarRecursos = recursoRepository.findOne(id);
		if (deletarRecursos == null) {
			System.out.println("Não foi possivel deletar recurso com id: " + id + ", id não encontrado");
			return new ResponseEntity<Recursos>(HttpStatus.NOT_FOUND);
		}
		recursoRepository.delete(id);
		return new ResponseEntity<Recursos>(HttpStatus.NO_CONTENT);
	}


}
