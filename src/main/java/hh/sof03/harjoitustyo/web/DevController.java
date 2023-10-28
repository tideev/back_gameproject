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

@Controller

public class DevController {

       @Autowired
    DeveloperRepository devRepo;

      @GetMapping(value="/developers")
    public String getAllCategories(Model model) {

        model.addAttribute("developers",devRepo.findAll()); 
        return "developers";
    }

     @RequestMapping(value = "/adddeveloper")
    public String addDev(Model model){
    	model.addAttribute("developer", new Developer());
        return "adddeveloper";
    }   

    @PostMapping(value = "/savedeveloper")
    public String saveDev(@ModelAttribute("developer") Developer dev) {
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
        return "redirect:/developers";
    }

    @GetMapping(value = "/editdev/{id}")
    public String editDev(@PathVariable("id") Long devId, Model model) {

        Developer dev = devRepo.findById(devId).orElse(null);

        if (dev != null) {
            model.addAttribute("developer", dev);
            return "editdeveloper";
        } else {
            return "redirect:/developers"; 
        }
    }

    @GetMapping(value = "/deleteDeveloper/{devId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDev(@PathVariable("devId") Long devId, Model model) {
    	devRepo.deleteById(devId);
        return "redirect:../developers";
    }      
    
}
