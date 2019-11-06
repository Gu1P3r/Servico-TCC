package tcc.PluriGame.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcc.PluriGame.domain.model.Professores;

@Repository
public interface ProfessoresRepository extends JpaRepository<Professores, Long>{

}
