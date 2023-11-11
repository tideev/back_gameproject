package hh.sof03.harjoitustyo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.sof03.harjoitustyo.web.DevController;
import hh.sof03.harjoitustyo.web.DevRestController;
import hh.sof03.harjoitustyo.web.GameController;
import hh.sof03.harjoitustyo.web.GameRestController;
import hh.sof03.harjoitustyo.web.ReviewController;
import hh.sof03.harjoitustyo.web.ReviewRestController;
import hh.sof03.harjoitustyo.web.UserProfileRestController;
import hh.sof03.harjoitustyo.web.userProfileController;

@SpringBootTest
class HarjoitustyoApplicationTests {

	@Autowired
	private GameController gController;

	@Autowired
	private GameRestController gRestController;

	@Autowired
	private DevController dController;

	@Autowired
	private DevRestController dRestController;

	@Autowired
	private ReviewController rController;

	@Autowired
	private ReviewRestController rRestController;

	@Autowired
	private userProfileController upController;

	@Autowired
	private UserProfileRestController upRestController;

	@Test
	void contextLoads() throws Exception {
		assertThat(gController).isNotNull();
		assertThat(gRestController).isNotNull();
		assertThat(dController).isNotNull();
		assertThat(dRestController).isNotNull();
		assertThat(rController).isNotNull();
		assertThat(rRestController).isNotNull();
		assertThat(upController).isNotNull();
		assertThat(upRestController).isNotNull();
	}

}
