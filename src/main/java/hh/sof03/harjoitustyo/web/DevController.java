package hh.sof03.harjoitustyo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.validation.Valid;

@Controller

public class DevController {

    @Autowired
    DeveloperRepository devRepo;

    @Autowired
    GameRepository gRepo;

    @GetMapping(value = "/developerlist")
    public String getAllDevs(Model model) {

        model.addAttribute("developers", devRepo.findAll());
        return "developerlist";
    }

    @RequestMapping(value = "/adddeveloper")
    public String addDev(Model model) {
        model.addAttribute("developer", new Developer());
        return "adddeveloper";
    }

    @PostMapping(value = "/savedeveloper")
    public String saveDev(@ModelAttribute("developer") @Valid Developer dev, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "adddeveloper";
        } else {
            if (dev.getDevId() == null) {

                devRepo.save(dev);

            } else {

                Developer existingDev = devRepo.findById(dev.getDevId()).orElse(null);

                if (existingDev != null) {

                    existingDev.setName(dev.getName());
                    existingDev.setYear(dev.getYear());
                    existingDev.setCountry(dev.getCountry());

                    devRepo.save(existingDev);
                }
            }
            return "redirect:/developerlist";
        }
    }

    @GetMapping(value = "/editdev/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editDev(@PathVariable("id") Long devId, Model model) {

        Developer dev = devRepo.findById(devId).orElse(null);

        if (dev != null) {
            model.addAttribute("developer", dev);
            return "editdeveloper";
        } else {
            return "redirect:/developerlist";
        }
    }

    @GetMapping(value = "/deleteDeveloper/{devId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDev(@PathVariable("devId") Long devId, Model model) {
        Developer developer = devRepo.findById(devId).orElse(null);

        if (developer != null) {
            // Käy läpi kaikki pelit ja poista niiden liittyvä kehittäjä
            List<Game> games = gRepo.findByDeveloper(developer);
            for (Game game : games) {
                game.setDeveloper(null);
                gRepo.save(game);
            }

            devRepo.deleteById(devId);
        }

        return "redirect:../developerlist";
    }

    @GetMapping(value = "/searchdev")
    public String searchDevs(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword, Model model) {

        Iterable<Developer> devIterable;

        if (searchType != null && keyword != null) {
            switch (searchType) {
                case "name":
                    devIterable = devRepo.findByNameContainingIgnoreCase(keyword);
                    break;
                case "country":
                    devIterable = devRepo.findByCountryContainingIgnoreCase(keyword);
                    break;
                case "year":
                    devIterable = devRepo.findByYear(Integer.parseInt(keyword));
                    break;
                default:
                    devIterable = devRepo.findAll();
                    break;
            }
        } else {
            devIterable = devRepo.findAll();
        }

        List<Developer> devs = new ArrayList<>();
        devIterable.forEach(devs::add);

        model.addAttribute("developers", devs);
        return "developerlist";
    }
}
