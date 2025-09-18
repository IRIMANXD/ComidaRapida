package lp2.fisi.irimanovel.entidades;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "generos")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long idGenero;

    @Column(name = "nombre_genero", nullable = false, unique = true)
    private String nombreGenero;

    @ManyToMany(mappedBy = "generos")
    private Set<Novela> novelas;

    // Getters y setters...
    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public Set<Novela> getNovelas() {
        return novelas;
    }

    public void setNovelas(Set<Novela> novelas) {
        this.novelas = novelas;
    }
} 