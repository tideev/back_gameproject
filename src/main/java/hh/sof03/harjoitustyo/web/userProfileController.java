package hh.sof03.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import hh.sof03.harjoitustyo.domain.User;
import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.UserRepository;
import jakarta.validation.Valid;
import hh.sof03.harjoitustyo.domain.ReviewRepository;

@Controller
public class userProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/userprofile")
    public String userProfile(Model model) {
        // Kirjautunut käyttäjä
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        List<Review> review = reviewRepository.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("reviews", review);
        return "userprofile";
    }

    @GetMapping("/edituser")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "edituserprofile";
    }

    @PostMapping("/saveuser")
    public String updateProfile(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edituserprofile";
        } else {
            User existingUser = userRepository.findByUserId(user.getUserId());
            if (existingUser != null) {
                existingUser.setEmail(user.getEmail());
                existingUser.setUsername(user.getUsername());

                userRepository.save(existingUser);
                return "redirect:/userprofile";
            } else {
                return "edituserprofile";
            }
        }
    }
}
