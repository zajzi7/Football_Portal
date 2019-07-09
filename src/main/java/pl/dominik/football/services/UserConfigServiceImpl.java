package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.entity.UserConfig;
import pl.dominik.football.domain.repository.UserConfigRepository;

import java.util.Optional;

@Service
public class UserConfigServiceImpl implements UserConfigService {

    @Autowired
    UserConfigRepository userConfigRepository;

    @Autowired
    SeasonService seasonService;

    @Override
    public void createUserConfig() {
        UserConfig userConfig = new UserConfig();
        userConfigRepository.save(userConfig);
    }

    @Override
    public UserConfig getUserConfig() {

        Optional<UserConfig> result = userConfigRepository.findById(1);

        UserConfig userConfig = null;

        if (result.isPresent()) {
            userConfig = result.get();
        } else throw new RuntimeException("User Config not found");

        return userConfig;
    }

    @Override
    public void setCurrentSeason(Season season) {
        UserConfig userConfig = getUserConfig();
        userConfig.setCurrentSeason(season);
        userConfigRepository.save(userConfig);
    }

    @Override
    public void setFavouriteTeam(Team team) {
        UserConfig userConfig = getUserConfig();
        userConfig.setFavouriteTeam(team);
        userConfigRepository.save(userConfig);
    }

    @Override
    public Team getFavouriteTeam() {
        return getUserConfig().getFavouriteTeam();
    }

    @Override
    public int getCurrentSeasonId() {
        return getUserConfig().getCurrentSeason().getId();
    }

    @Override
    public void setCurrentSeasonById(int id) {
        Season season = seasonService.getSeasonById(id);
        setCurrentSeason(season);
    }

    @Override
    public boolean getImportantMessageFlag() {
        //return true if the important message flag is set to true
        return getUserConfig().isImportantMessageFlag();
    }

    @Override
    public void setImportantMessageFlag(boolean flag) {
        UserConfig userConfig = getUserConfig();
        userConfig.setImportantMessageFlag(flag);
        userConfigRepository.save(userConfig);
    }

    @Override
    public String getImportantMessageContent() {
        return getUserConfig().getImportantMessageContent();
    }

    @Override
    public void setImportantMessageContent(String content) {
        UserConfig userConfig = getUserConfig();
        userConfig.setImportantMessageContent(content);
        userConfigRepository.save(userConfig);
    }

}
