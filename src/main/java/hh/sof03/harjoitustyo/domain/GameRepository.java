package hh.sof03.harjoitustyo.domain;

import org.springframework.data.repository.CrudRepository;


public interface GameRepository extends CrudRepository <Game, Long> {
    Game findByTitle(String title);
}
