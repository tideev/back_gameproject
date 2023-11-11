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

import hh.sof03.harjoitustyo.domain.Developer;
import hh.sof03.harjoitustyo.domain.DeveloperRepository;

@CrossOrigin
@Controller
public class DevRestController {

    @Autowired
    private DeveloperRepository devRepository;

    @GetMapping(value = "/developers")
    public @ResponseBody List<Developer> devListRest() {
        return (List<Developer>) devRepository.findAll();
    }

    @GetMapping(value = "/developers/{id}")
    public @ResponseBody Optional<Developer> findDevRest(@PathVariable("id") Long id) {
        return devRepository.findById(id);
    }

    @PostMapping(value = "/developers")
    public @ResponseBody Developer saveDevRest(@RequestBody Developer developer) {
        return devRepository.save(developer);
    }

}
