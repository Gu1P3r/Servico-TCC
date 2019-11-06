package tcc.PluriGame.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tcc.PluriGame.domain.model.Cursos;

@Repository
public interface CursosRepository extends JpaRepository<Cursos, Long>{


}
