package pl.dominik.football.domain.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RankingRepository {

    @PersistenceContext
    EntityManager em;

    public void createRanking() {

    }
}
