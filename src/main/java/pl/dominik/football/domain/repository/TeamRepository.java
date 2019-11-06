package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team getTeamByTeamNameEquals(String teamName);

    List<Team> getTeamsBySeason_Id(int seasonId);

}