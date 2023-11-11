package hh.sof03.harjoitustyo.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);

	User findByUserId(Long userId);

}