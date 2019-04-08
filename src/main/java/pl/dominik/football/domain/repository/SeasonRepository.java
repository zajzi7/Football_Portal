package pl.dominik.football.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.Round;
import pl.dominik.football.domain.Season;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SeasonRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createSeason(String seasonName) {
        Season season = new Season(seasonName);
        em.persist(season);
    }

    @Transactional
    //Method gets current rounds in season and adds a new one into it
    public void addRound(Round round, Season season) {
        List<Round> rounds = season.getRounds();
        rounds.add(round);
        season.setRounds(rounds);
        em.merge(season);
    }

    public Season getSeason(String seasonValue) {
        Season season = em.createQuery("from Season s where s.seasonName=:seasonValue", Season.class)
                .setParameter("seasonValue", seasonValue)
                .getSingleResult();
        return season;
    }

    public Season getSeasonById(int id) {
        return em.find(Season.class, id);
    }

    public void saveSeason(Season season) {
        em.merge(season);
    }

    public List<Season> getSeasonList() {
        return em.createQuery("from Season", Season.class).getResultList();
    }

    @Transactional
    public void deleteSeason(int id) {
        em.remove(em.find(Season.class, id));
//        em.createQuery("delete from Season s where s.id=:id").setParameter("id", id).executeUpdate();
    }

    @Transactional
    public void updateSeason(int id, Season season) {
        em.merge(season);
    }

}
