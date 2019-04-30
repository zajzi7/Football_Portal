package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;

import java.util.Set;

public interface SeasonRepository extends JpaRepository<Season, Integer> {

    Season getSeasonBySeasonNameEquals(String seasonName);

    Set<Season> getSeasonsByTeamsEquals(Team team);
}
