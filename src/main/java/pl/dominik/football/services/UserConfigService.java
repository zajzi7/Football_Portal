package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.UserConfigRepository;

@Service
public class UserConfigService {

    @Autowired
    @Lazy
    UserConfigRepository userConfigRepository;

    public void setCurrentSeason(Season season) {
        userConfigRepository.setCurrentSeason(season);
    }

    public void setFavouriteTeam(Team team) {
        userConfigRepository.setFavouriteTeam(team);
    }

    public Team getFavouriteTeam() {
        return userConfigRepository.getFavouriteTeam();
    }

    public int getCurrentSeasonId() {
        return userConfigRepository.getCurrentSeasonId();
    }

    public void setCurrentSeasonById(int id) {
        userConfigRepository.setCurrentSeasonById(id);
    }

    public int getId() {
        return userConfigRepository.getId();
    }

}
