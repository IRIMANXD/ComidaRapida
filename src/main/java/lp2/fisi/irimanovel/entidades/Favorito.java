package lp2.fisi.irimanovel.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
public class Favorito implements Serializable {
    @EmbeddedId
    private FavoritoId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idNovela")
    @JoinColumn(name = "id_novela")
    private Novela novela;

    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;

    // Getters y setters...
} 