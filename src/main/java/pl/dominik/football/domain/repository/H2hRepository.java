package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.H2h;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;

import java.util.List;

public interface H2hRepository extends JpaRepository<H2h, Integer> {

    List<H2h> getH2hsByOppositeTeam(Team team);

    List<H2h> getH2hsByOppositeTeamAndSeason(Team team, Season season);

    H2h getH2hByOppositeTeamAndRanking(Team team, RankingData ranking);

    List<H2h> getH2hsBySeason(Season season);
}
