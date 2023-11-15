package hh.sof03.harjoitustyo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sof03.harjoitustyo.domain.Developer;
import hh.sof03.harjoitustyo.domain.DeveloperRepository;
import hh.sof03.harjoitustyo.domain.Game;
import hh.sof03.harjoitustyo.domain.GameRepository;
import hh.sof03.harjoitustyo.domain.Review;
import hh.sof03.harjoitustyo.domain.ReviewRepository;
import hh.sof03.harjoitustyo.domain.UserRepository;
import hh.sof03.harjoitustyo.domain.User;

@SpringBootApplication
public class HarjoitustyoApplication {

	private static final Logger log = LoggerFactory.getLogger(HarjoitustyoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HarjoitustyoApplication.class, args);
	}

	@Bean
	public CommandLineRunner gameDeo(GameRepository gameRepo, DeveloperRepository devRepo, UserRepository userRepo,
			ReviewRepository reviewRepo) {
		return (args) -> {
			log.info("save a couple of games");
			Developer dev1 = new Developer("Riot Games", "United States", 2006);
			devRepo.save(dev1);
			Developer dev2 = new Developer("Valve Corporation", "United States", 1996);
			devRepo.save(dev2);
			Developer dev3 = new Developer("Raven Software", "United States", 1990);
			devRepo.save(dev3);

			gameRepo.save(new Game("Valorant",
					"Valorant is a free-to-play first-person tactical hero shooter developed and published by Riot Games, for Windows.",
					2020, dev1));
			gameRepo.save(new Game("CS-GO",
					"Counter-Strike is a series of multiplayer tactical first-person shooter video games in which teams of terrorists battle to perpetrate an act of terror while counter-terrorists try to prevent it.",
					2000, dev2));
			gameRepo.save(new Game("League of Legends",
					"League of Legends, commonly referred to as League, is a 2009 multiplayer online battle arena video game developed and published by Riot Games.",
					2009, dev1));

			gameRepo.save(new Game("Call of Duty: Warzone",
					"Call of Duty: Warzone was a free-to-play battle royale video game developed by Raven Software and published by Activision.",
					2020, dev3));

			User user1 = new User("user1", "$2a$10$XER4GeYqfDIrye.VFtDEoOowyJxp/tsBHoa3.Bgz.1UicZq70P8k.", "USER",
					"user1@user.com");
			User user2 = new User("user2", "$2a$10$XER4GeYqfDIrye.VFtDEoOowyJxp/tsBHoa3.Bgz.1UicZq70P8k.", "USER",
					"user2@user.com");
			User user3 = new User("user3", "$2a$10$XER4GeYqfDIrye.VFtDEoOowyJxp/tsBHoa3.Bgz.1UicZq70P8k.", "USER",
					"user3@user.com");
			User user4 = new User("admin1", "$2a$10$l0n.4Sqg1zRmzJegOUf3dOMuX80J4n.3JNJssO3PntKfaRJ4zx8aO", "ADMIN",
					"admin1@admin.com");
			userRepo.save(user1);
			userRepo.save(user2);
			userRepo.save(user3);
			userRepo.save(user4);

			Review review1 = new Review(5, "Great tactical shooter!", gameRepo.findByTitle("Valorant"), user1);
			Review review2 = new Review(4, "Enjoyable gameplay and graphics.", gameRepo.findByTitle("Valorant"), user2);
			Review review3 = new Review(4, "Classic and competitive.", gameRepo.findByTitle("CS-GO"), user3);
			Review review4 = new Review(3, "Needs some improvements.", gameRepo.findByTitle("CS-GO"), user2);
			Review review5 = new Review(5, "Highly addictive MOBA.", gameRepo.findByTitle("League of Legends"), user1);
			Review review6 = new Review(4, "Frequent updates and events.", gameRepo.findByTitle("League of Legends"),
					user3);
			Review review7 = new Review(4, "Exciting battle royale experience.",
					gameRepo.findByTitle("Call of Duty: Warzone"), user3);
			Review review8 = new Review(2, "Some technical issues.", gameRepo.findByTitle("Call of Duty: Warzone"),
					user1);
			Review review9 = new Review(1, "Needs some technical improvements and new gamemodes.",
					gameRepo.findByTitle("Call of Duty: Warzone"),
					user4);
			Review review10 = new Review(4, "Fun game!",
					gameRepo.findByTitle("Valorant"),
					user4);

			reviewRepo.save(review1);
			reviewRepo.save(review2);
			reviewRepo.save(review3);
			reviewRepo.save(review4);
			reviewRepo.save(review5);
			reviewRepo.save(review6);
			reviewRepo.save(review7);
			reviewRepo.save(review8);
			reviewRepo.save(review9);
			reviewRepo.save(review10);

			log.info("fetch all Developers");
			for (Developer developer : devRepo.findAll()) {
				log.info(developer.toString());
			}

			log.info("fetch all games");
			for (Game game : gameRepo.findAll()) {
				log.info(game.toString());

			}
		};

	}

}
