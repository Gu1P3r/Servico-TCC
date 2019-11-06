package tcc.PluriGame.domain.resource;

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

import tcc.PluriGame.domain.model.Role;
import tcc.PluriGame.domain.model.UsuarioRole;
import tcc.PluriGame.domain.model.Usuarios;
import tcc.PluriGame.domain.repository.RoleRepository;
import tcc.PluriGame.domain.repository.UsuarioRoleRepository;
import tcc.PluriGame.domain.repository.UsuariosRepository;
import tcc.PluriGame.domain.security.services.UserService;

@RestController
@RequestMapping("/usuarios") // rota de acesso
public class UsuariosResource {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UsuariosRepository usuariosRepository;
	@Autowired
	private UsuarioRoleRepository usuarioRoleRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Usuarios> addUsuarios(@Valid @RequestBody Usuarios usuarios) {
		
		Usuarios usuariosnew = userService.save(usuarios);
		
		if(usuariosnew.getTipo() == 1) {
			
				long tipo = 1;
				Role idRole = roleRepository.findOne(tipo);
				
				UsuarioRole userRole = new UsuarioRole(usuariosnew, idRole);
				
				usuarioRoleRepository.save(userRole);

		}
		
		if(usuariosnew.getTipo() == 2) {
		
			long tipo = 2;
			Role idRole = roleRepository.findOne(tipo);
			
			
			UsuarioRole userRole = new UsuarioRole(usuariosnew, idRole);
			
		
			usuarioRoleRepository.save(userRole);
			
		}
		
		if(usuariosnew.getTipo() == 3) {
			
				long tipo = 3;
				Role idRole = roleRepository.findOne(tipo);
				
				UsuarioRole userRole = new UsuarioRole(usuariosnew, idRole);
				
				usuarioRoleRepository.save(userRole);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuariosnew);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados")
	public ResponseEntity<List<Usuarios>> getUsuarios() {
		List<Usuarios> getAllUsuarios = usuariosRepository.findAll();
		System.out.println(getAllUsuarios);
		if (getAllUsuarios.isEmpty()) {
			return new ResponseEntity<List<Usuarios>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Usuarios>>(getAllUsuarios, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ALUNO', 'ADMIN', 'PROF')")
	@GetMapping(value = "/dados/{id}")
	public ResponseEntity<Usuarios> getUsuario(@PathVariable("id") Long id) {
		Usuarios getUsuarios = usuariosRepository.findOne(id);
		if (getUsuarios == null) {
			System.out.println("Usuario com id " + id + "não encontrado");
			return new ResponseEntity<Usuarios>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuarios>(getUsuarios, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Usuarios> updateUsuarios(@PathVariable("id") Long id, @Valid @RequestBody Usuarios usuarios) {
		System.out.println("Procurando Usuario de id " + id);

		Usuarios usuariosAtual = usuariosRepository.findOne(id);

		if (usuariosAtual == null) {
			System.out.println("Usuario com id " + id + " não encontrado");
			return new ResponseEntity<Usuarios>(HttpStatus.NOT_FOUND);
		}
		BeanUtils.copyProperties(usuarios, usuariosAtual, "id"); // observacao
		usuariosAtual.setTipo(usuariosAtual.getTipo());	
		usuariosAtual.setEmail(usuariosAtual.getEmail());
		usuariosAtual.setSenha(usuariosAtual.getSenha());
		userService.save(usuarios);
		return new ResponseEntity<Usuarios>(usuarios, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<Usuarios> deleteUsuarios(@PathVariable("id") long id) {
		System.out.println("Procurando Usuario de id " + id);

		Usuarios deletarUsuarios = usuariosRepository.findOne(id);
		if (deletarUsuarios == null) {
			System.out.println("Não foi possivel deletar usuario com id: " + id + ", id não encontrado");
			return new ResponseEntity<Usuarios>(HttpStatus.NOT_FOUND);
		}
		userService.delete(id);
		return new ResponseEntity<Usuarios>(HttpStatus.NO_CONTENT);
	}

}
