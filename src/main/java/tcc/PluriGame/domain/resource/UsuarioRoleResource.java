package tcc.PluriGame.domain.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tcc.PluriGame.domain.model.Role;
import tcc.PluriGame.domain.model.Usuarios;
import tcc.PluriGame.domain.model.UsuarioRole;
import tcc.PluriGame.domain.repository.RoleRepository;
import tcc.PluriGame.domain.repository.UsuariosRepository;
import tcc.PluriGame.domain.repository.UsuarioRoleRepository;

@RestController
public class UsuarioRoleResource {
	
	@Autowired
	private UsuariosRepository usuarioRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	private UsuarioRoleRepository usuarioRoleRepository;
	
	@CrossOrigin(origins = "*")
	public ResponseEntity<UsuarioRole> addRole(@PathVariable("id") Long idusuario, @PathVariable("id") Long idrole,
			@Valid @RequestBody UsuarioRole usuarioRole) {
		
		Usuarios usuario = usuarioRepository.findOne(idusuario);
		Role role = roleRepository.findOne(idrole);
	
		UsuarioRole newPermission = new UsuarioRole(usuario, role);
		usuarioRoleRepository.save(newPermission);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newPermission);
		
	}
	

}
