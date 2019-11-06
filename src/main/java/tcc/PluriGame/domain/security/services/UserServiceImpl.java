package tcc.PluriGame.domain.security.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import tcc.PluriGame.domain.model.Usuarios;
import tcc.PluriGame.domain.repository.UsuariosRepository;
import tcc.PluriGame.domain.security.services.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UsuariosRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	@Override
	public Usuarios save(Usuarios user) {
	
		Usuarios newUsuario = new Usuarios();
		newUsuario.setTipo(user.getTipo());
		newUsuario.setEmail(user.getEmail());
		newUsuario.setSenha(bCryptPasswordEncoder.encode(user.getSenha()));
		System.out.println(newUsuario);
		return usuarioRepository.save(newUsuario);
		
	}
	  
	@Override
	public List<Usuarios> findAll() {
		List<Usuarios> list = new ArrayList<>();
		usuarioRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		usuarioRepository.delete(id);

	}

	@Override
	public Usuarios findOne(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public Usuarios updatePass(long id, Usuarios user) {
		Usuarios updateUsuario = new Usuarios();
		return null;
	}

	@Override
	public Usuarios findById(Long id) {
		return usuarioRepository.findOne(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuarios usuario = usuarioRepository.findByEmail(email);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario ou senha invalida!!");
		}
		return new User(usuario.getEmail(), usuario.getSenha(), getAuthority(usuario));
	}

	private Set<SimpleGrantedAuthority> getAuthority(Usuarios usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

}
