package hh.sof03.harjoitustyo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping(value="/games")
    public String getAllGames(Model model) {

        model.addAttribute("games", gRepo.findAll()); 
        return "games";
    }

     @RequestMapping(value = "/add")
    public String addGame(Model model){
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

 
}
