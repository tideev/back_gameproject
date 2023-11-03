package hh.sof03.harjoitustyo.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    Game findByTitle(String title);

    // Haku otsikon osittaisen vastineen perusteella (huomioiden kirjainkoko)
    List<Game> findByTitleContainingIgnoreCase(String title);

    // Haku kehittäjän nimen osittaisen vastineen perusteella (huomioiden kirjainkoko)
    List<Game> findByDeveloperNameContainingIgnoreCase(String name);

    // Haku julkaisuvuoden perusteella
    List<Game> findByYear(int year);
}
