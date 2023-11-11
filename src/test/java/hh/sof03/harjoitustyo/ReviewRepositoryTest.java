package hh.sof03.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof03.harjoitustyo.domain.Game;
import hh.sof03.harjoitustyo.domain.GameRepository;
import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.ReviewRepository;
import hh.sof03.harjoitustyo.domain.User;
import hh.sof03.harjoitustyo.domain.UserRepository;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository revRepository;

    @Autowired
    GameRepository gRepository;

    @Autowired
    UserRepository uRepository;

    @Test // testataan findByUser()-metodin toimivuutta
    public void testFindByUser() {
        List<Review> reviews = revRepository.findByReviewId(1L);

        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getRating()).isEqualTo(5);
        assertThat(reviews.get(0).getGame().getTitle()).isEqualTo("Valorant");
    }

    @Test // testataansave()-metodin toimivuutta
    public void testCreateNewReview() {
        // Tarkistetaan että tietokannasta löytyy olemassa oleva peli ja käyttäjä
        Game existingGame = gRepository.findByTitle("Valorant");
        User existingUser = uRepository.findByUsername("user1");
        assertThat(existingGame).isNotNull();
        assertThat(existingUser).isNotNull();

        Review review = new Review(2, "Great test!", existingGame, existingUser);
        revRepository.save(review);

        assertThat(review.getReviewId()).isNotNull();
    }

    @Test // testataan delete()-metodin toimivuutta
    public void testDeleteReview() {
        Game existingGame = gRepository.findByTitle("Valorant");
        User existingUser = uRepository.findByUsername("user1");
        assertThat(existingGame).isNotNull();
        assertThat(existingUser).isNotNull();

        Review review = new Review(2, "Great test!", existingGame, existingUser);
        revRepository.save(review);

        Long revId = review.getReviewId();
        assertThat(revRepository.findById(revId)).isPresent();

        revRepository.deleteById(revId);
        assertThat(revRepository.findById(revId)).isEmpty();
    }

}
