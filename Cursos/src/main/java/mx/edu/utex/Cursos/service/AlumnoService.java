package mx.edu.utex.Cursos.service;

import mx.edu.utex.Cursos.model.Alumno;
import mx.edu.utex.Cursos.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    private AlumnoRepository repo;

    public AlumnoService(AlumnoRepository repo){
        this.repo = repo;
    }

    public List<Alumno> getAll(){
        return repo.findAll();
    }

    public Optional<Alumno> findById(Long id){
        return repo.findById(id);
    }

    public Alumno save(Alumno alumno){
        return repo.save(alumno);
    }

    public Optional<Alumno> update(Long id, Alumno alumno){
        Optional<Alumno> optional = repo.findById(id);
        if (optional.isEmpty()){
            return optional.empty();
        }
        Alumno alumnoDb = optional.get();

        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellidoPaterno(alumno.getApellidoPaterno());
        alumnoDb.setApellidoMaterno(alumno.getApellidoMaterno());

        return Optional.of(repo.save(alumnoDb));
    }

    public boolean delete(Long id){
        if (!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
