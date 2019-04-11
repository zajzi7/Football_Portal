package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Season;

public interface SeasonRepository extends JpaRepository<Season, Integer> {

    Season getSeasonBySeasonNameEquals(String seasonName);

}
