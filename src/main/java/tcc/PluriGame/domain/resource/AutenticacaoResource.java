package tcc.PluriGame.domain.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import tcc.PluriGame.domain.model.AuthToken;
import tcc.PluriGame.domain.repository.RoleRepository;
import tcc.PluriGame.domain.repository.UsuariosRepository;
import tcc.PluriGame.domain.security.jwt.JwtProvider;
import tcc.PluriGame.domain.security.model.LoginForm;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AutenticacaoResource {
	
	@Autowired
	AuthenticationManager authenticationManager;
	 
	@Autowired
	UsuariosRepository usuarioRepository;
	 
	@Autowired
	RoleRepository roleRepository;
	 
	@Autowired
	JwtProvider jwtProvider;

	@CrossOrigin(origins = "*")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
	 
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
	 
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	 
	    String jwt = jwtProvider.generateToken(authentication);
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	 
	    return ResponseEntity.ok(new AuthToken(jwt));
	  }
	

}
