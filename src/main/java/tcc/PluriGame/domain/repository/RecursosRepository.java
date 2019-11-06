package tcc.PluriGame.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcc.PluriGame.domain.model.Recursos;

@Repository
public interface RecursosRepository extends JpaRepository<Recursos, Long>{
	
	Recursos findOne(Long id);

}
