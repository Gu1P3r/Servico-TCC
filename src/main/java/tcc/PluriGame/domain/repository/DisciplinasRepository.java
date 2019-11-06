package tcc.PluriGame.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcc.PluriGame.domain.model.Disciplinas;

@Repository
public interface DisciplinasRepository extends JpaRepository<Disciplinas, Long> {

}
