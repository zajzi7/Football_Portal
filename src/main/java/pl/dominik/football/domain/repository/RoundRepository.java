package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Round;

public interface RoundRepository extends JpaRepository<Round, Integer> {

    Round getRoundByRoundNumberEquals(int roundNumber);

}
