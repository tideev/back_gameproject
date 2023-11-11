package hh.sof03.harjoitustyo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;
    @NotNull(message = "Rating between 1-5 is required")
    private int rating;
    @NotBlank(message = "Comment is required")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties("reviews")
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne
    @JsonIgnoreProperties("reviews")
    @JoinColumn(name = "userId")
    private User user;

    public Review() {
        this.rating = 0;
        this.comment = null;
    }

    public Review(int rating, String comment, Game game, User user) {
        this.rating = rating;
        this.comment = comment;
        this.game = game;
        this.user = user;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int raiting) {
        this.rating = raiting;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review [reviewId=" + reviewId + ", rating=" + rating + ", comment=" + comment + ", game="
                + this.getGame() + ", user=" + this.getUser() + "]";
    }
}
