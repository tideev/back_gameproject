package hh.sof03.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof03.harjoitustyo.domain.User;
import hh.sof03.harjoitustyo.domain.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository uRepository;

    @Test // testataan UserRepon save()- ja findByUsername()-metodien toimivuutta
    public void testCreateAndSearchUser() {
        User user = new User("testuser", "passwordhash", "USER", "test@example.com");
        uRepository.save(user);
        assertThat(user.getUserId()).isNotNull();

        User foundUser = uRepository.findByUsername("testuser");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
    }

    @Test // testataan UserRepon delete()-metodin toimivuutta
    public void testDeleteUser() {
        User user = new User("testuser", "passwordhash", "USER", "test@example.com");
        uRepository.save(user);

        Long userId = user.getUserId();
        assertThat(uRepository.findById(userId)).isPresent();

        uRepository.deleteById(userId);
        assertThat(uRepository.findById(userId)).isEmpty();
    }
}
