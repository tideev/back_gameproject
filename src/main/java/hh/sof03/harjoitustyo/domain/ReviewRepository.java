package hh.sof03.harjoitustyo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByGameGameId(Long gameId); // Use the correct property name for the game id
}