package pl.dominik.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@ComponentScan({"pl.dominik.football.services", "pl.dominik.football.domain.repository", "pl.dominik.football.controllers.accessible", "pl.dominik.football.controllers.admin", "pl.dominik.football.utilities"})
@SpringBootApplication
@EntityScan("pl.dominik.football.domain.entity")
@EnableTransactionManagement
@EnableScheduling
@EnableJpaRepositories("pl.dominik.football.domain.repository")
public class FootballApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FootballApplication.class, args);
	}

}
