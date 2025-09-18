package lp2.fisi.irimanovel.repositorios;

import lp2.fisi.irimanovel.entidades.Novela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

public interface NovelaRepository extends JpaRepository<Novela, Long> {
    // Métodos personalizados si es necesario
    Page<Novela> findAll(Pageable pageable);
} 