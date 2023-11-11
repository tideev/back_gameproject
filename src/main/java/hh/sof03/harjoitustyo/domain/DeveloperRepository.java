package hh.sof03.harjoitustyo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeveloperRepository extends CrudRepository<Developer, Long> {

      List<Developer> findByName(String name);

      List<Developer> findByNameContainingIgnoreCase(String name);

      List<Developer> findByCountryContainingIgnoreCase(String country);

      List<Developer> findByYear(int year);
}
