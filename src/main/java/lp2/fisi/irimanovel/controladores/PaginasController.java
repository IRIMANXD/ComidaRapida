package lp2.fisi.irimanovel.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import lp2.fisi.irimanovel.repositorios.NovelaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.PageRequest;
import lp2.fisi.irimanovel.repositorios.CapituloRepository;
import lp2.fisi.irimanovel.entidades.Usuario;
import lp2.fisi.irimanovel.entidades.Novela;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import lp2.fisi.irimanovel.repositorios.UsuarioRepository;

@Controller
public class PaginasController {
    @Autowired
    private NovelaRepository novelaRepository;
    @Autowired
    private lp2.fisi.irimanovel.repositorios.CapituloRepository capituloRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        var topTen = PageRequest.of(0, 10);
        var novelas = novelaRepository.findAll(topTen).getContent();
        model.addAttribute("novelas", novelas);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            model.addAttribute("imagenPerfil", usuario.getImagenPerfil());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registrarse")
    public String registrarse() {
        return "registrarse";
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Obtener el usuario con sus favoritos inicializados
        Usuario usuarioConFavoritos = usuarioRepository.findByIdWithFavoritas(usuario.getIdUsuario()).orElse(usuario);
        
        model.addAttribute("usuario", usuarioConFavoritos);
        model.addAttribute("biblioteca", usuarioConFavoritos.getNovelasFavoritas());
        return "perfil";
    }

    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "editar-perfil";
    }

    @GetMapping("/novelas/pagina/{id}")
    public String verNovela(@PathVariable Integer id, Model model, HttpSession session) {
        var novela = novelaRepository.findById(id.longValue()).orElse(null);
        model.addAttribute("novela", novela);
        
        if (novela != null) {
            var capitulos = capituloRepository.findByNovelaIdNovela(novela.getIdNovela());
            model.addAttribute("capitulos", capitulos);
            
            // Verificar si la novela está en favoritos del usuario
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            boolean enFavoritos = false;
            if (usuario != null) {
                Usuario usuarioConFavoritos = usuarioRepository.findByIdWithFavoritas(usuario.getIdUsuario()).orElse(null);
                if (usuarioConFavoritos != null) {
                    enFavoritos = usuarioConFavoritos.getNovelasFavoritas().contains(novela);
                }
            }
            model.addAttribute("enFavoritos", enFavoritos);
        }
        
        return "novela-detalle";
    }

    @GetMapping("/novelas/pagina/{idNovela}/capitulo/{numCapitulo}")
    public String verCapitulo(@PathVariable Long idNovela, @PathVariable Integer numCapitulo, Model model) {
        var novela = novelaRepository.findById(idNovela).orElse(null);
        if (novela == null) return "redirect:/";
        var capitulos = capituloRepository.findByNovelaIdNovela(idNovela);
        var capitulo = capitulos.stream().filter(c -> c.getNumero().equals(numCapitulo)).findFirst().orElse(null);
        if (capitulo == null) return "redirect:/novelas/pagina/" + idNovela;
        model.addAttribute("novela", novela);
        model.addAttribute("capitulo", capitulo);
        model.addAttribute("capitulos", capitulos);
        return "capitulo";
    }

    @PostMapping("/api/biblioteca/favorito")
    @ResponseBody
    public String marcarFavorito(@RequestBody Map<String, Object> payload, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "error";
        
        Long novelaId = Long.valueOf(payload.get("novelaId").toString());
        boolean favorito = Boolean.parseBoolean(payload.get("favorito").toString());
        
        Novela novela = novelaRepository.findById(novelaId).orElse(null);
        if (novela == null) return "error";
        
        // Obtener el usuario con sus favoritos inicializados
        Usuario usuarioConFavoritos = usuarioRepository.findByIdWithFavoritas(usuario.getIdUsuario()).orElse(null);
        if (usuarioConFavoritos == null) return "error";
        
        if (favorito) {
            usuarioConFavoritos.getNovelasFavoritas().add(novela);
        } else {
            usuarioConFavoritos.getNovelasFavoritas().remove(novela);
        }
        
        usuarioRepository.save(usuarioConFavoritos);
        session.setAttribute("usuarioLogueado", usuarioConFavoritos);
        return "ok";
    }

    @GetMapping("/api/usuario/logueado")
    @ResponseBody
    public String usuarioLogueado(HttpSession session) {
        return (session.getAttribute("usuarioLogueado") != null) ? "si" : "no";
    }

    @GetMapping("/novelas/populares")
    public String novelasPopulares(HttpSession session, Model model) {
        var novelas = novelaRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "rating"));
        model.addAttribute("novelas", novelas);
        model.addAttribute("populares", true);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            model.addAttribute("imagenPerfil", usuario.getImagenPerfil());
        }
        return "index";
    }
} 