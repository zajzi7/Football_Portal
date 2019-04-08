package pl.dominik.football.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.Match;
import pl.dominik.football.domain.Round;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoundRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createRound(int roundNumber) {
        Round round = new Round(roundNumber);
        em.persist(round);
    }

    @Transactional
    //Method gets current matches in round and adds a new one into it
    public void addMatch(Match match, Round round) {
        List<Match> matches = round.getMatches();
        matches.add(match);
        round.setMatches(matches);
        em.merge(round);
    }

    public Round getRoundById(int id) {
        return em.find(Round.class, id);
    }

    public Round getRoundByInt(int roundNumber) {
        Round round = em.createQuery("from Round r where r.roundNumber=:roundNumber", Round.class)
                .setParameter("roundNumber", roundNumber)
                .getSingleResult();
        return round;
    }

    public List<Match> getMatchesByRound(Round round) {
        return em.createQuery("from Match m where m.round=:round", Match.class)
                .setParameter("round", round)
                .getResultList();
    }




}
