package hh.sof03.harjoitustyo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hh.sof03.harjoitustyo.domain.Developer;
import hh.sof03.harjoitustyo.domain.DeveloperRepository;
import hh.sof03.harjoitustyo.domain.Game;
import hh.sof03.harjoitustyo.domain.GameRepository;

@Controller
public class GameController {

    @Autowired
    GameRepository gRepo;

    @Autowired
    DeveloperRepository dRepo;

    @GetMapping(value = "/games")
    public String getAllGames(Model model) {

        // Kirjautuneen käyttäjän tiedot
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUser = userDetails.getUsername();
        model.addAttribute("loggedInUser", loggedInUser);

        model.addAttribute("games", gRepo.findAll());
        return "games";
    }

    @RequestMapping(value = "/add")
    public String addGame(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("developers", dRepo.findAll());
        return "addgame";
    }

    @PostMapping(value = "/save")
    public String saveGame(@ModelAttribute("game") Game game) {
        if (game.getGameId() == null) {

            Long developerId = game.getDeveloper().getDevId();
            Developer dev = dRepo.findById(developerId).orElse(null);
            game.setDeveloper(dev);

            gRepo.save(game);

        } else {
            Game existingGame = gRepo.findById(game.getGameId()).orElse(null);

            if (existingGame != null) {
                existingGame.setTitle(game.getTitle());
                existingGame.setDescription(game.getDescription());
                existingGame.setYear(game.getYear());

                Long developerId = game.getDeveloper().getDevId();
                Developer dev = dRepo.findById(developerId).orElse(null);
                existingGame.setDeveloper(dev);

                gRepo.save(existingGame);
            }
        }
        return "redirect:/games";
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editGame(@PathVariable("id") Long gameId, Model model) {
        Game game = gRepo.findById(gameId).orElse(null);

        if (game != null) {
            model.addAttribute("game", game);
            model.addAttribute("developers", dRepo.findAll());
            return "editgame";
        } else {
            return "redirect:/games";
        }
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteGame(@PathVariable("id") Long gameId, Model model) {
        gRepo.deleteById(gameId);
        return "redirect:../games";
    }

    @GetMapping(value = "/search")
    public String searchGames(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword, Model model) {

        Iterable<Game> gameIterable;

        if (searchType != null && keyword != null) {
            switch (searchType) {
                case "title":
                    gameIterable = gRepo.findByTitleContainingIgnoreCase(keyword);
                    break;
                case "developer":
                    gameIterable = gRepo.findByDeveloperNameContainingIgnoreCase(keyword);
                    break;
                case "year":
                    gameIterable = gRepo.findByYear(Integer.parseInt(keyword));
                    break;
                default:
                    gameIterable = gRepo.findAll();
                    break;
            }
        } else {
            gameIterable = gRepo.findAll();
        }

        List<Game> games = new ArrayList<>();
        gameIterable.forEach(games::add);

        model.addAttribute("games", games);
        return "games";
    }
}
