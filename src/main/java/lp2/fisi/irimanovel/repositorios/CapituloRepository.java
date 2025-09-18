package lp2.fisi.irimanovel.repositorios;

import lp2.fisi.irimanovel.entidades.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CapituloRepository extends JpaRepository<Capitulo, Long> {
    List<Capitulo> findByNovelaIdNovela(Long idNovela);
    List<Capitulo> findByNovelaIdNovelaOrderByNumeroAsc(Long idNovela);
} 