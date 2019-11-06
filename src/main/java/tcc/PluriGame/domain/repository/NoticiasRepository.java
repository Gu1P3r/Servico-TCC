package tcc.PluriGame.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcc.PluriGame.domain.model.Noticias;

@Repository
public interface NoticiasRepository extends JpaRepository<Noticias, Long>{
	
}
