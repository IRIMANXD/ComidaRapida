package lp2.fisi.irimanovel.repositorios;

import lp2.fisi.irimanovel.entidades.Favorito;
import lp2.fisi.irimanovel.entidades.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {
    // Métodos personalizados si es necesario
} 