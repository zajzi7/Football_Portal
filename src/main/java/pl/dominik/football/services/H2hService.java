package pl.dominik.football.services;

import pl.dominik.football.domain.entity.H2h;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;

import java.util.List;

public interface H2hService {

    void saveH2h(H2h h2h);

    List<H2h> getH2hsByOppositeTeam(Team team);

    List<H2h> getH2hsByOppositeTeamAndSeason(Team team, Season season);

    void deleteById(int id);

    void delete(H2h h2h);

    RankingData generateH2h(RankingData ranking);

    H2h getH2hByOppositeTeamAndRanking(Team awayTeam, RankingData ranking);

}
