package lp2.fisi.irimanovel.repositorios;

import lp2.fisi.irimanovel.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Métodos personalizados si es necesario
} 