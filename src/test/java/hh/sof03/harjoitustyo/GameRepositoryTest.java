package hh.sof03.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof03.harjoitustyo.domain.Developer;
import hh.sof03.harjoitustyo.domain.DeveloperRepository;
import hh.sof03.harjoitustyo.domain.Game;
import hh.sof03.harjoitustyo.domain.GameRepository;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gRepository;

    @Autowired
    DeveloperRepository dRepository;

    @Test // testataan findByTitle()-metodin toimivuutta
    public void testFindByTitle() {
        Game games = gRepository.findByTitle("Valorant");

        assertThat(games.getYear()).isEqualTo(2020);
    }

    @Test // testataan save()-metodin toimivuutta
    public void testCreateNewGame() {
        // Tarkistetaan, että tietokannasta löytyy olemassa oleva dev
        Developer existingDev = dRepository.findByName("Riot Games").get(0);
        assertThat(existingDev).isNotNull();

        Game game = new Game("Testi", "Testi juujaa.", 2015, existingDev);
        gRepository.save(game);
        assertThat(game.getGameId()).isNotNull();
    }

    @Test // testataan delete()-metodin toimivuutta
    public void testDeleteGame() {
        Developer existingDev = dRepository.findByName("Riot Games").get(0);
        assertThat(existingDev).isNotNull();

        Game game = new Game("Testi", "Testi juujaa.", 2015, existingDev);
        gRepository.save(game);
        Long gameId = game.getGameId();
        assertThat(gRepository.findById(gameId)).isPresent();

        gRepository.deleteById(gameId);
        assertThat(gRepository.findById(gameId)).isEmpty();
    }

}
