package lp2.fisi.irimanovel.controladores;

import lp2.fisi.irimanovel.entidades.Favorito;
import lp2.fisi.irimanovel.entidades.FavoritoId;
import lp2.fisi.irimanovel.repositorios.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {
    @Autowired
    private FavoritoRepository favoritoRepository;

    @GetMapping
    public List<Favorito> listarFavoritos() {
        return favoritoRepository.findAll();
    }

    @PostMapping
    public Favorito crearFavorito(@RequestBody Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    @GetMapping("/{idUsuario}/{idNovela}")
    public Favorito obtenerFavorito(@PathVariable Long idUsuario, @PathVariable Long idNovela) {
        return favoritoRepository.findById(new FavoritoId(idUsuario, idNovela)).orElse(null);
    }

    @DeleteMapping("/{idUsuario}/{idNovela}")
    public void eliminarFavorito(@PathVariable Long idUsuario, @PathVariable Long idNovela) {
        favoritoRepository.deleteById(new FavoritoId(idUsuario, idNovela));
    }
} 