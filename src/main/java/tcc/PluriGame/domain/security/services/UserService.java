package tcc.PluriGame.domain.security.services;

import java.util.List;

import tcc.PluriGame.domain.model.Usuarios;


public interface UserService {
	
	Usuarios save(Usuarios user);
	List<Usuarios> findAll();
	void delete(long id);
	Usuarios findOne(String email);
	Usuarios updatePass(long id , Usuarios user);
	Usuarios findById(Long id);

}
