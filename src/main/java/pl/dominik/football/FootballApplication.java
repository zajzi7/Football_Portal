package pl.dominik.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("pl.dominik.football.domain.entity")
@EnableTransactionManagement
@EnableJpaRepositories("pl.dominik.football.domain.repository")
//@ComponentScan({"pl.dominik.football.services", "pl.dominik.football.domain.repository", "pl.dominik.football.controllers"})
public class FootballApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballApplication.class, args);
	}

}
