package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Round;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Integer> {

    Round getRoundByRoundNumberEquals(int roundNumber);

    List<Round> getRoundsBySeason_Id(int id);

}
