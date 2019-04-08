package pl.dominik.football.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RankingRepository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MatchRepository matchRepository;

//    public void createRanking(Season season) {
//        Ranking ranking = new Ranking(season);
//    }

    public void generateRanking() {
        
    }
}
