package hh.sof03.harjoitustyo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    private String title;
    private String description;
    @Column(name="release_year")
    private int year;

    @ManyToOne // Game @ManyToOne Developers
    @JsonIgnoreProperties("games")
    @JoinColumn(name = "developerId") //fk määritys Game-taulua varten
    private Developer developer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game") //Review OneToMany Games
    @JsonIgnoreProperties("game")
	private List<Review> reviews;

    public Game(){
        this.title = null;
        this.description = null;
        this.year = 0;

    }

    public Game( String title, String description, int year, Developer developer) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.developer = developer;
    }

    public Long getGameId() {
        return gameId;
    }
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

     public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    

    @Override
    public String toString() {
        if (this.developer != null)
        return "Game [gameId=" + gameId + ", title=" + title + ", description=" + description + ", year=" + year + ", developer=" + this.getDeveloper() + "]";

        else
            return  "Game [gameId=" + gameId + ", title=" + title + ", description=" + description + ", year=" + year +  "]";
    }
    
}
