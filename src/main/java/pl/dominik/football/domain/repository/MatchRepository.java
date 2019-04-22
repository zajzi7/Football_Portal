package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dominik.football.domain.entity.Match;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> getMatchesByRound_Id(int roundId);

}
