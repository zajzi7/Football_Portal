package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.entity.UserConfig;
import pl.dominik.football.domain.repository.UserConfigRepository;

@Service
public class UserConfigServiceImpl implements UserConfigService {

    @Autowired
    UserConfigRepository userConfigRepository;

    @Autowired
    SeasonService seasonService;

    public void createUserConfig() {
        UserConfig userConfig = new UserConfig();
        userConfigRepository.save(userConfig);
    }

    public void setCurrentSeason(Season season) {
        UserConfig userConfig = userConfigRepository.findByIdEquals(1);
        userConfig.setCurrentSeason(season);
        userConfigRepository.save(userConfig);
    }

    public void setFavouriteTeam(Team team) {
        UserConfig userConfig = userConfigRepository.findByIdEquals(1);
        userConfig.setFavouriteTeam(team);
        userConfigRepository.save(userConfig);
    }

    public Team getFavouriteTeam() {
        return userConfigRepository.getFavouriteTeam();
    }

    public int getCurrentSeasonId() {
        return userConfigRepository.getCurrentSeason().getId();
    }

    public void setCurrentSeasonById(int id) {
        Season season = seasonService.getSeasonById(id);
        setCurrentSeason(season);
    }

}
