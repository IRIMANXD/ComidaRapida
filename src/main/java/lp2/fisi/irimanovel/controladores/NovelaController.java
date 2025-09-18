package lp2.fisi.irimanovel.controladores;

import lp2.fisi.irimanovel.entidades.Novela;
import lp2.fisi.irimanovel.repositorios.NovelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novelas")
public class NovelaController {
    @Autowired
    private NovelaRepository novelaRepository;

    @GetMapping
    public List<Novela> listarNovelas() {
        return novelaRepository.findAll();
    }

    @PostMapping
    public Novela crearNovela(@RequestBody Novela novela) {
        return novelaRepository.save(novela);
    }

    @GetMapping("/{id}")
    public Novela obtenerNovela(@PathVariable Long id) {
        return novelaRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Novela actualizarNovela(@PathVariable Long id, @RequestBody Novela novela) {
        novela.setIdNovela(id);
        return novelaRepository.save(novela);
    }

    @DeleteMapping("/{id}")
    public void eliminarNovela(@PathVariable Long id) {
        novelaRepository.deleteById(id);
    }
} 