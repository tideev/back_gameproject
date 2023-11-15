package hh.sof03.harjoitustyo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import hh.sof03.harjoitustyo.domain.Game;
import hh.sof03.harjoitustyo.domain.GameRepository;
import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.ReviewRepository;
import hh.sof03.harjoitustyo.domain.User;
import hh.sof03.harjoitustyo.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reviewlist/{gameId}")
    public String showReviews(@PathVariable Long gameId, Model model) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            List<Review> reviews = reviewRepository.findByGameGameId(gameId);
            model.addAttribute("game", game.get());
            model.addAttribute("reviews", reviews);
            return "reviewlist";
        } else {
            return "redirect:/fpsgames";
        }
    }

    @GetMapping("/addreview/{gameId}")
    public String addReviewForm(@PathVariable Long gameId, Model model) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            Review review = new Review();
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            review.setGame(game.get());
            review.setUser(user);
            model.addAttribute("review", review);
            return "addreview";
        }
        return "redirect:/reviewlist";
    }

    @GetMapping(value = "/deleteReview/{reviewId}/{gameId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteReview(@PathVariable("reviewId") Long reviewId, @PathVariable("gameId") Long gameId,
            Model model) {
        reviewRepository.deleteById(reviewId);
        return "redirect:/reviewlist/" + gameId;
    }

    @PostMapping("/savereview/{gameId}")
    public String addReview(@ModelAttribute @Valid Review review, BindingResult bindingResult,
            @PathVariable Long gameId) {
        if (bindingResult.hasErrors()) {
            return "addreview";
        } else {
            Optional<Game> game = gameRepository.findById(gameId);
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (game.isPresent() && user != null) {
                review.setGame(game.get());
                review.setUser(user);
                reviewRepository.save(review);
            }
            return "redirect:/reviewlist/" + gameId;
        }

    }

    @GetMapping("/reviewstats/{gameId}")
    @ResponseBody
    //Vastauksena lähetetään JSON-muotoinen Map, joka sisältää String ja Object.
    public ResponseEntity<Map<String, Object>> getReviewStats(@PathVariable Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            List<Review> reviews = reviewRepository.findByGameGameId(gameId);
            
            //Lasketaan keskiarvo arvosanoille ja arvostelujen yhteismäärä.
            double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
            long totalReviews = reviews.size();

            //Luodaan uusi HashMap, johon tallennetaan arvostelutilastot ja lisätään tilastot.
            Map<String, Object> stats = new HashMap<>();
            stats.put("averageRating", averageRating);
            stats.put("totalReviews", totalReviews);

            return ResponseEntity.ok(stats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}