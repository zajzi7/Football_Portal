package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team getTeamByTeamNameEquals(String teamName);

}