package lp2.fisi.irimanovel.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "novelas")
public class Novela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_novela")
    private Long idNovela;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "autor")
    private String autor;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "portada_url")
    private String portadaUrl;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "rating")
    private String rating;

    @Column(name = "resumen")
    private String resumen;

    @Column(name = "capitulos")
    private Integer capitulos;

    @OneToMany(mappedBy = "novela", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Capitulo> capitulosList;

    @ManyToMany
    @JoinTable(
        name = "novela_genero",
        joinColumns = @JoinColumn(name = "id_novela"),
        inverseJoinColumns = @JoinColumn(name = "id_genero")
    )
    private Set<Genero> generos;

    @ManyToMany(mappedBy = "novelasFavoritas")
    private Set<Usuario> usuariosQueLaTienenComoFavorita;

    // Getters y setters...
    public Long getIdNovela() {
        return idNovela;
    }

    public void setIdNovela(Long idNovela) {
        this.idNovela = idNovela;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Integer getCapitulos() {
        return capitulos;
    }
    public void setCapitulos(Integer capitulos) {
        this.capitulos = capitulos;
    }

    public java.util.List<Capitulo> getCapitulosList() { return capitulosList; }
    public void setCapitulosList(java.util.List<Capitulo> capitulosList) { this.capitulosList = capitulosList; }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }

    public Set<Usuario> getUsuariosFavoritos() {
        return usuariosQueLaTienenComoFavorita;
    }

    public void setUsuariosFavoritos(Set<Usuario> usuariosFavoritos) {
        this.usuariosQueLaTienenComoFavorita = usuariosFavoritos;
    }
} 