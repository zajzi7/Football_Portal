package pl.dominik.football.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.dominik.football.domain.Season;
import pl.dominik.football.domain.UserConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserConfigRepository {

    @PersistenceContext
    EntityManager em;

    UserConfig userConfig = new UserConfig();

    @Autowired
    SeasonRepository seasonRepository;

    //Admin manages which season is on going
    @Transactional
    public void setCurrentSeason(Season season) {
        userConfig.setCurrentSeason(season);
        em.merge(userConfig);
    }

    @Transactional
    public void setCurrentSeasonById(int id) {
        Season season = seasonRepository.getSeasonById(id);
        setCurrentSeason(season);
    }

    public int getCurrentSeasonId() {
        Season season =
                em.createQuery("select uc.currentSeason from UserConfig uc where uc.id=1", Season.class)
                .getSingleResult();
        return season.getId();
    }

}
