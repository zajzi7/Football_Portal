package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;

import java.time.LocalDate;
import java.util.List;

public interface RoundService {

    void assignRoundToSeasonWithId(Round round, int seasonId);

    void createRound(int roundNumber, LocalDate date);

    void saveRound(Round round);

    Round getRoundByInt(int roundNumber);

    Round getRoundById(int id);

    List<Round> getRoundsBySeasonId(int id);

    void deleteRound(int id);

    int getSeasonIdByRoundId(int roundId);

    LocalDate generateNextRoundDefaultDate(Season season);
}
