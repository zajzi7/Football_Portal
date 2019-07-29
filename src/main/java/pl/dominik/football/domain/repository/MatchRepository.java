package pl.dominik.football.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Team;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> getMatchesByRound_Id(int roundId);

    @Query("select m from Match m where (m.awayTeam =  ?1 or m.homeTeam = ?1) " +
            "and ((m.matchDate = ?2 and m.matchStartTime < ?3) or m.matchDate < ?2)" +
            "order by m.matchDate desc, m.matchStartTime desc")
    List<Match> findPreviousMatch(Team favouriteTeam, LocalDate date, LocalTime time);


    @Query("select m from Match m where (m.awayTeam =  ?1 or m.homeTeam = ?1) " +
            "and ((m.matchDate = ?2 and m.matchStartTime > ?3) or m.matchDate > ?2) " +
            "order by m.matchDate asc, m.matchStartTime asc")
    List<Match> findNextMatch(Team favouriteTeam, LocalDate date, LocalTime time);

    @Query("select m from Match m where (m.awayTeam =  ?1 or m.homeTeam = ?1) and m.matchDate <= ?2 " +
            "order by m.matchDate desc, m.matchStartTime desc")
    List<Match> findLast5Matches(Team team, LocalDate date, PageRequest pageRequest);

}
