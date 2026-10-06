package mx.edu.utex.Cursos.repository;

import mx.edu.utex.Cursos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}
