package lp2.fisi.irimanovel.controladores;

import jakarta.servlet.http.HttpSession;
import lp2.fisi.irimanovel.entidades.Usuario;
import lp2.fisi.irimanovel.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.time.LocalDateTime;

@Controller
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String usuario, @RequestParam String password, HttpSession session) {
        Usuario user = usuarioRepository.findByNombreUsuarioAndContrasena(usuario, password);
        if (user != null) {
            user.setActivo("si");
            usuarioRepository.save(user);
            session.setAttribute("usuarioLogueado", user);
            return "ok";
        } else {
            return "error";
        }
    }

    @PostMapping("/registrarse")
    @ResponseBody
    public String registrarse(
        @RequestParam String nombreCompleto,
        @RequestParam String correo,
        @RequestParam String usuario,
        @RequestParam String password
    ) {
        if (usuarioRepository.existsByNombreUsuario(usuario)) {
            return "usuario";
        }
        if (usuarioRepository.existsByCorreo(correo)) {
            return "correo";
        }
        if (usuarioRepository.existsByNombreCompleto(nombreCompleto)) {
            return "nombreCompleto";
        }
        Usuario nuevo = new Usuario();
        nuevo.setNombreCompleto(nombreCompleto);
        nuevo.setCorreo(correo);
        nuevo.setNombreUsuario(usuario);
        nuevo.setContrasena(password);
        nuevo.setFechaRegistro(LocalDateTime.now());
        usuarioRepository.save(nuevo);
        return "ok";
    }

    @PostMapping("/actualizar-perfil")
    @ResponseBody
    public String actualizarPerfil(
        @RequestParam String nombreCompleto,
        @RequestParam String correo,
        @RequestParam String usuario,
        @RequestParam(value = "imagenPerfil", required = false) MultipartFile imagenPerfil,
        HttpSession session
    ) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "error";
        }
        
        // Verificar si el correo ya existe en otro usuario
        if (!correo.equals(usuarioLogueado.getCorreo()) && usuarioRepository.existsByCorreo(correo)) {
            return "correo";
        }
        
        // Verificar si el nombre de usuario ya existe en otro usuario
        if (!usuario.equals(usuarioLogueado.getNombreUsuario()) && usuarioRepository.existsByNombreUsuario(usuario)) {
            return "usuario";
        }
        
        // Manejar la subida de imagen
        if (imagenPerfil != null && !imagenPerfil.isEmpty()) {
            try {
                // Crear directorio si no existe
                String uploadDir = "src/main/resources/static/uploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                // Generar nombre único para el archivo
                String originalFilename = imagenPerfil.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = UUID.randomUUID().toString() + fileExtension;
                
                // Guardar archivo
                Path filePath = Paths.get(uploadDir + newFilename);
                Files.write(filePath, imagenPerfil.getBytes());
                
                // Actualizar la ruta en la base de datos
                usuarioLogueado.setImagenPerfil("/uploads/" + newFilename);
                
            } catch (IOException e) {
                return "error";
            }
        }
        
        // Actualizar datos
        usuarioLogueado.setNombreCompleto(nombreCompleto);
        usuarioLogueado.setCorreo(correo);
        usuarioLogueado.setNombreUsuario(usuario);
        
        usuarioRepository.save(usuarioLogueado);
        session.setAttribute("usuarioLogueado", usuarioLogueado);
        
        return "ok";
    }

    @PostMapping("/eliminar-cuenta")
    @ResponseBody
    public String eliminarCuenta(HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "error";
        }
        usuarioRepository.deleteById(usuarioLogueado.getIdUsuario());
        session.invalidate();
        return "ok";
    }

    @PostMapping("/logout")
    @ResponseBody
    public void logout(HttpSession session) {
        session.invalidate();
    }
} 