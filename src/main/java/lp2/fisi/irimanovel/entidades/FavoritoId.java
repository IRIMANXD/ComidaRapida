package lp2.fisi.irimanovel.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoritoId implements Serializable {
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_novela")
    private Long idNovela;

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritoId that = (FavoritoId) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idNovela, that.idNovela);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idNovela);
    }

    // Constructor público para uso en el controlador
    public FavoritoId(Long idUsuario, Long idNovela) {
        this.idUsuario = idUsuario;
        this.idNovela = idNovela;
    }

    // Getters y setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdNovela() { return idNovela; }
    public void setIdNovela(Long idNovela) { this.idNovela = idNovela; }
} 