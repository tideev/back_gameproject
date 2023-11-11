package hh.sof03.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.harjoitustyo.domain.Game;
import hh.sof03.harjoitustyo.domain.GameRepository;

@CrossOrigin
@Controller
public class GameRestController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping(value = "/games")
    public @ResponseBody List<Game> gameListRest() {
        return (List<Game>) gameRepository.findAll();
    }

    @GetMapping(value = "/games/{id}")
    public @ResponseBody Optional<Game> findGameRest(@PathVariable("id") Long id) {
        return gameRepository.findById(id);
    }

    @PostMapping(value = "/games")
    public @ResponseBody Game saveGameRest(@RequestBody Game game) {
        return gameRepository.save(game);
    }

}
