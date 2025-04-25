package org.gorditodev.gestiondecursos.repository;

import org.gorditodev.gestiondecursos.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {


}
