package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Player;

import java.util.List;

public interface PlayerService {

    void createPlayer(String firstName, String lastName);

    void deletePlayer(int id);

    List<Player> getAllPlayers();


}
