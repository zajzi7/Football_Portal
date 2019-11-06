package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Team;

import java.util.List;

public interface RankingDataRepository extends JpaRepository<RankingData, Integer> {

    RankingData getRankingDataByTeamIdAndSeasonId(int teamId, int seasonId);

    List<RankingData> getRankingDataBySeasonId(int seasonId);

    RankingData getRankingDataByTeam(Team team);

}
