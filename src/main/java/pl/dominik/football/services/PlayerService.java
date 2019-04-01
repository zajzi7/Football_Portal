package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.Player;
import pl.dominik.football.domain.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public void createPlayer(String firstName, String lastName) {
        playerRepository.createPlayer(firstName, lastName);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.getAllPlayers();
    }

}
