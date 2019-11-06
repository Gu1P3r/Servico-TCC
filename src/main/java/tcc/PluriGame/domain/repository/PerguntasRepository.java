package tcc.PluriGame.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcc.PluriGame.domain.model.Perguntas;

@Repository
public interface PerguntasRepository extends JpaRepository<Perguntas, Long>{
	
}
