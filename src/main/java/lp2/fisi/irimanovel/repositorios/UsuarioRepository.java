package lp2.fisi.irimanovel.repositorios;

import lp2.fisi.irimanovel.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombreUsuarioAndContrasena(String nombreUsuario, String contrasena);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
    boolean existsByNombreCompleto(String nombreCompleto);
    
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.novelasFavoritas WHERE u.idUsuario = :id")
    Optional<Usuario> findByIdWithFavoritas(@Param("id") Integer id);
} 