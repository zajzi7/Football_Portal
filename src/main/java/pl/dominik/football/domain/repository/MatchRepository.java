package pl.dominik.football.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.Match;
import pl.dominik.football.domain.Round;
import pl.dominik.football.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MatchRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void addMatchResult(Team homeTeam, Team awayTeam, int homeScore, int awayScore, Round round) {
        Match match = new Match(homeTeam, awayTeam, homeScore, awayScore, round);
        em.persist(match);
    }

    public List<Match> getMatchResult() {
        return em.createQuery("from Match", Match.class).getResultList();
    }

    public Match getMatchById(int id) {
        return em.find(Match.class, id);
    }

}
