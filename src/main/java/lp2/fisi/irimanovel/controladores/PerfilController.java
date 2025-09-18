package lp2.fisi.irimanovel.controladores;

import lp2.fisi.irimanovel.entidades.Usuario;
import lp2.fisi.irimanovel.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public Usuario obtenerPerfil(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
} 