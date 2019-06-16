package pl.dominik.football.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;

import java.time.LocalDate;
import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Integer> {

    Round getRoundByRoundNumberEquals(int roundNumber);

    List<Round> getRoundsBySeason_Id(int id);

    @Query("select r from Round r where r.season = :season and r.roundStartDate < :date order by r.roundStartDate desc")
    List<Round> findLastRound(@Param("season") Season season, @Param("date") LocalDate date);

}
