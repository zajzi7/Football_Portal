package pl.dominik.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.dominik.football.domain.Match;
import pl.dominik.football.domain.repository.PlayerRepository;

@Component
public class Start implements CommandLineRunner {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void run(String... args) throws Exception {

//        playerRepository.createPlayer("Dominik", "Drabina");
        //        System.out.println("Hello");

    }
}
