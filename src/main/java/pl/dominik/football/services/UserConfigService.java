package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.Season;
import pl.dominik.football.domain.repository.UserConfigRepository;

@Service
public class UserConfigService {

    @Autowired
    UserConfigRepository userConfigRepository;

    public void setCurrentSeason(Season season) {
        userConfigRepository.setCurrentSeason(season);
    }

    public int getCurrentSeasonId() {
        return userConfigRepository.getCurrentSeasonId();
    }

    public void setCurrentSeasonById(int id) {
        userConfigRepository.setCurrentSeasonById(id);
    }
}
