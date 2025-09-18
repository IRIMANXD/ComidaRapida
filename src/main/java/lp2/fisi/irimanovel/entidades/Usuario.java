package lp2.fisi.irimanovel.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    @Column(name = "correo", unique = true, nullable = false)
    private String correo;

    @Column(name = "contrase\u00f1a", nullable = false)
    private String contrasena;

    @Column(name = "imagen_perfil")
    private String imagenPerfil;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo", length = 2)
    private String activo = "no";

    @ManyToMany
    @JoinTable(
        name = "favoritos",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_novela")
    )
    private Set<Novela> novelasFavoritas;

    // Getters y setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getImagenPerfil() { return imagenPerfil; }
    public void setImagenPerfil(String imagenPerfil) { this.imagenPerfil = imagenPerfil; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getActivo() { return activo; }
    public void setActivo(String activo) { this.activo = activo; }
    public Set<Novela> getNovelasFavoritas() { return novelasFavoritas; }
    public void setNovelasFavoritas(Set<Novela> novelasFavoritas) { this.novelasFavoritas = novelasFavoritas; }
} 