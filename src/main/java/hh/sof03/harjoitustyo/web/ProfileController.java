package hh.sof03.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import hh.sof03.harjoitustyo.domain.User;
import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.UserRepository;
import hh.sof03.harjoitustyo.domain.ReviewRepository;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/profile/{username}")
    public String userProfile(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("reviews", user.getReviews());
        return "profile";
    }

    @GetMapping("/profile/{username}/edit")
    public String editProfile(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/profile/{username}/edit")
    public String updateProfile(@PathVariable String username, @ModelAttribute User updatedUser) {
        User user = userRepository.findByUsername(username);
    
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);
        return "redirect:/profile/" + username;
    }
}
