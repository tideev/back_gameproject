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

import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.ReviewRepository;

@CrossOrigin
@Controller
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping(value = "/reviews")
    public @ResponseBody List<Review> reviewListRest() {
        return (List<Review>) reviewRepository.findAll();
    }

    @GetMapping(value = "/reviews/{id}")
    public @ResponseBody Optional<Review> findReviewRest(@PathVariable("id") Long id) {
        return reviewRepository.findById(id);
    }

    @PostMapping(value = "/reviews")
    public @ResponseBody Review saveReviewRest(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

}
