package lp2.fisi.irimanovel.controladores;

import lp2.fisi.irimanovel.entidades.Genero;
import lp2.fisi.irimanovel.repositorios.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    @Autowired
    private GeneroRepository generoRepository;

    @GetMapping
    public List<Genero> listarGeneros() {
        return generoRepository.findAll();
    }

    @PostMapping
    public Genero crearGenero(@RequestBody Genero genero) {
        return generoRepository.save(genero);
    }

    @GetMapping("/{id}")
    public Genero obtenerGenero(@PathVariable Long id) {
        return generoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Genero actualizarGenero(@PathVariable Long id, @RequestBody Genero genero) {
        genero.setIdGenero(id);
        return generoRepository.save(genero);
    }

    @DeleteMapping("/{id}")
    public void eliminarGenero(@PathVariable Long id) {
        generoRepository.deleteById(id);
    }
} 