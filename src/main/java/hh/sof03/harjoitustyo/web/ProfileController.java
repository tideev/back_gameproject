package hh.sof03.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import hh.sof03.harjoitustyo.domain.User;
import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.UserRepository;
import jakarta.validation.Valid;
import hh.sof03.harjoitustyo.domain.ReviewRepository;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping( value ="/profile/{username}")
    public String userProfile(@PathVariable("username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("reviews", user.getReviews());
        return "profile";
    }

    @GetMapping(value = "/edit/{username}")
    public String editProfile(@PathVariable("username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/save/{username}")
    public String updateProfile(@PathVariable("username") String username, @ModelAttribute @Valid User updatedUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editprofile";
        } else{
       
        User user = userRepository.findByUsername(username);
        
        // Tarkista ja päivitä käyttäjän sähköposti
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
    
        userRepository.save(user);
        return "redirect:/profile/" + username;
    }
}
    
}
