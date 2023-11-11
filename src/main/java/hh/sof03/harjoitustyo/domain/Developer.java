package hh.sof03.harjoitustyo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long devId;
    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name;
    @NotBlank(message = "Country is required")
    String country;
    @NotNull(message = "Year is required")
    @Column(name = "foundation_year")
    private int year;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer") // Category OneToMany Games
    @JsonIgnoreProperties("developer")
    private List<Game> games;

    public Developer() {
        this.name = null;
        this.country = null;
        this.year = 0;
    }

    public Developer(String name, String country, int year) {
        this.name = name;
        this.country = country;
        this.year = year;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Developer [devId=" + devId + ", name=" + name + ", country=" + country + ", year=" + year + "]";
    }

}
