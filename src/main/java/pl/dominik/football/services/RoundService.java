package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Round;

public interface RoundService {

    void createRound(int roundNumber);

    Round getRoundByInt(int roundNumber);

    Round getRoundById(int id);

}
