package lp2.fisi.irimanovel;

import lp2.fisi.irimanovel.entidades.Novela;
import lp2.fisi.irimanovel.entidades.Capitulo;
import lp2.fisi.irimanovel.repositorios.NovelaRepository;
import lp2.fisi.irimanovel.repositorios.CapituloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final NovelaRepository novelaRepository;
    private final CapituloRepository capituloRepository;

    public DataLoader(NovelaRepository novelaRepository, CapituloRepository capituloRepository) {
        this.novelaRepository = novelaRepository;
        this.capituloRepository = capituloRepository;
    }

    @Override
    public void run(String... args) {
        // Crear novelas si no existen
        Novela n1 = createNovelaIfNotExists("¡Otros suben de nivel, pero yo sigo con el cultivo!", 
            "Novela China - Oriental, Fantasía, Acción, Aventura", 
            "The reclusive mysterious seven", 
            LocalDate.of(2023, 1, 1), 
            "/img/portada1.png", 
            "⭐⭐⭐⭐☆", 
            "Un joven persiste en el cultivo mientras otros suben de nivel.");

        Novela n2 = createNovelaIfNotExists("Destrozaré todo con habilidades pasivas", 
            "Novela Coreana - Fantasía, Acción, Harén, Reencarnación", 
            "Autor Coreano", 
            LocalDate.of(2022, 5, 10), 
            "/img/portada2.png", 
            "⭐⭐⭐⭐⭐", 
            "Un protagonista con habilidades pasivas domina el mundo.");

        Novela n3 = createNovelaIfNotExists("30 años después de reencarnar, ¡resulta que este mundo era un Rofan!", 
            "Novela Coreana - Acción, Aventura, Comedia, Drama, Fantasía, Harén, Artes Marciales, Recuentos de la Vida", 
            "Autor Coreano", 
            LocalDate.of(2021, 8, 15), 
            "/img/portada3.png", 
            "⭐⭐⭐⭐☆", 
            "Después de 30 años reencarnado, descubre que el mundo es un Rofan.");

        Novela n4 = createNovelaIfNotExists("Mi sistema de vampiros", 
            "Novela Inglesa - Acción, Fantasía, Misterio", 
            "Autor Inglés", 
            LocalDate.of(2020, 12, 20), 
            "/img/portada4.png", 
            "⭐⭐⭐⭐☆", 
            "Un sistema de vampiros cambia la vida del protagonista.");

        Novela n5 = createNovelaIfNotExists("Novela de magia y dragones", 
            "Fantasía épica con dragones y magos poderosos", 
            "Autor Fantástico", 
            LocalDate.of(2019, 3, 12), 
            "/img/portada5.png", 
            "⭐⭐⭐⭐⭐", 
            "Una aventura mágica en un mundo de dragones.");

        Novela n6 = createNovelaIfNotExists("El guerrero inmortal", 
            "Acción, aventura y batallas épicas", 
            "Autor de Acción", 
            LocalDate.of(2018, 7, 22), 
            "/img/portada6.png", 
            "⭐⭐⭐⭐", 
            "Un guerrero que nunca muere enfrenta su destino.");

        Novela n7 = createNovelaIfNotExists("Romance en la academia", 
            "Romance juvenil en una escuela mágica", 
            "Autor Romántico", 
            LocalDate.of(2020, 2, 14), 
            "/img/portada7.png", 
            "⭐⭐⭐⭐", 
            "Amor y magia se mezclan en la academia.");

        Novela n8 = createNovelaIfNotExists("El misterio del bosque oscuro", 
            "Misterio y terror en un bosque encantado", 
            "Autor de Misterio", 
            LocalDate.of(2017, 10, 31), 
            "/img/portada8.png", 
            "⭐⭐⭐", 
            "Un grupo de amigos descubre los secretos del bosque.");

        Novela n9 = createNovelaIfNotExists("Viaje al futuro", 
            "Ciencia ficción y aventuras temporales", 
            "Autor de Ciencia Ficción", 
            LocalDate.of(2021, 6, 5), 
            "/img/portada9.png", 
            "⭐⭐⭐⭐", 
            "Un joven viaja al futuro y cambia la historia.");

        Novela n10 = createNovelaIfNotExists("La leyenda del héroe caído", 
            "Drama, acción y redención", 
            "Autor Dramático", 
            LocalDate.of(2016, 11, 11), 
            "/img/portada10.png", 
            "⭐⭐⭐⭐", 
            "Un héroe caído busca redimirse ante el mundo.");

        // Crear capítulos para cada novela
        createChaptersForNovela(n1, "¡Otros suben de nivel, pero yo sigo con el cultivo!");
        createChaptersForNovela(n2, "Destrozaré todo con habilidades pasivas");
        createChaptersForNovela(n3, "30 años después de reencarnar, ¡resulta que este mundo era un Rofan!");
        createChaptersForNovela(n4, "Mi sistema de vampiros");
        createChaptersForNovela(n5, "Novela de magia y dragones");
        createChaptersForNovela(n6, "El guerrero inmortal");
        createChaptersForNovela(n7, "Romance en la academia");
        createChaptersForNovela(n8, "El misterio del bosque oscuro");
        createChaptersForNovela(n9, "Viaje al futuro");
        createChaptersForNovela(n10, "La leyenda del héroe caído");

        System.out.println("Novelas y capítulos de prueba insertados correctamente.");
    }

    private Novela createNovelaIfNotExists(String titulo, String descripcion, String autor, 
                                         LocalDate fechaPublicacion, String portadaUrl, 
                                         String rating, String resumen) {
        return novelaRepository.findAll().stream()
            .filter(n -> n.getTitulo().equals(titulo))
            .findFirst()
            .orElseGet(() -> {
                Novela novela = new Novela();
                novela.setTitulo(titulo);
                novela.setDescripcion(descripcion);
                novela.setAutor(autor);
                novela.setFechaPublicacion(fechaPublicacion);
                novela.setPortadaUrl(portadaUrl);
                novela.setFechaCreacion(LocalDateTime.now());
                novela.setRating(rating);
                novela.setResumen(resumen);
                novela.setCapitulos(2); // Inicialmente 2 capítulos
                return novelaRepository.save(novela);
            });
    }

    private void createChaptersForNovela(Novela novela, String novelaTitle) {
        // Verificar si ya existen capítulos para esta novela
        if (capituloRepository.findByNovelaIdNovela(novela.getIdNovela()).isEmpty()) {
            // Capítulo 1
            Capitulo capitulo1 = new Capitulo();
            capitulo1.setNumero(1);
            capitulo1.setTitulo("Prólogo");
            capitulo1.setContenido("Capítulo 1 - Prólogo\n\n" +
                "País de la Gran Xia, Kioto.\n\n" +
                "La institución más importante, el Politécnico de Kioto, es una residencia de estudiantes masculinos.\n\n" +
                "[Ding~]\n\n" +
                "Un sonido abrupto apareció en la mente de Su Xing.\n\n" +
                "Mientras Su Xing empacaba su equipo, preparándose para ingresar a la mazmorra de la instancia para farmear oro, se congeló.\n\n" +
                "¿Quién anda ahí? ¿Quién me está gastando una broma?\n\n" +
                "Su Xing miró alrededor del dormitorio vacío, pero no encontró nada.\n\n" +
                "Entonces, una serie de voces resonaron en su mente.\n\n" +
                "[Conectando con el Simulador de Vida…]\n\n" +
                "[¡Enlace exitoso!]\n\n" +
                "[Anfitrión: Su Xing]\n\n" +
                "[Edad: 21 años]\n\n" +
                "[Ocupación: Cultivador]\n\n" +
                "[Número de simulaciones: 1]\n\n" +
                "[Energía: 100]\n\n" +
                "[Introducción: El Simulador de Vida simulará tu futuro completamente basándose en tu trayectoria de desarrollo en el mundo real. Tras la simulación, podrás obtener recompensas. Cada simulación consumirá cierta cantidad de energía. El conteo de simulaciones se actualiza semanalmente.]");
            capitulo1.setFechaPublicacion(LocalDate.now());
            capitulo1.setNovela(novela);
            capituloRepository.save(capitulo1);

            // Capítulo 2
            Capitulo capitulo2 = new Capitulo();
            capitulo2.setNumero(2);
            capitulo2.setTitulo("El comienzo de la aventura");
            capitulo2.setContenido("Capítulo 2 - El comienzo de la aventura\n\n" +
                "Al ver esto, Su Xing quedó atónito.\n\n" +
                "Su Nianan era su hermana, y habían crecido juntas como dos gotas de agua. ¿Cómo pudo morir de repente?\n\n" +
                "Lleno de confusión, Su Xing continuó leyendo.\n\n" +
                "[Tus padres te dijeron que Su Nianan murió durante una sesión de nivelación de mazmorra.]\n\n" +
                "[Estabas abrumado por el dolor, pasabas tus días ahogando tus penas en alcohol, viviendo en un estupor borracho.]\n\n" +
                "[Tu buen hermano Yu Yan te encontró y sintió pesar por la muerte de tu hermana.]\n\n" +
                "[Él te consoló y saliste de lo profundo de tu dolor.]\n\n" +
                "[Te dio una gran suma de dinero, que invertiste en una tienda de equipo profesional.]\n\n" +
                "[Rápidamente hiciste una fortuna y te convertiste en un comerciante de equipos de segunda mano bastante famoso en Kioto.]\n\n" +
                "[Tres años después, llegó otra terrible noticia: tu buen hermano Yu Yan también había muerto.]\n\n" +
                "Su Xing se quedó en silencio después de leer esto.\n\n" +
                "Yu Yan era su compañero de cuarto en la universidad y su mejor hermano en la universidad.\n\n" +
                "Además, Yu Yan era un profesional oculto, considerado un genio en toda la Universidad de Kioto, ¿cómo pudo haber muerto así?\n\n" +
                "[Asististe al funeral de Yu Yan.]\n\n" +
                "[En el funeral, se enteró de que Yu Yan murió en un calabozo.]\n\n" +
                "Este era el comienzo de una aventura que cambiaría para siempre la vida de Su Xing...");
            capitulo2.setFechaPublicacion(LocalDate.now());
            capitulo2.setNovela(novela);
            capituloRepository.save(capitulo2);
        }
    }
} 