package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.AdminConfig;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.AdminConfigRepository;

import java.util.Optional;

@Service
public class AdminConfigServiceImpl implements AdminConfigService {

    @Autowired
    AdminConfigRepository adminConfigRepository;

    @Autowired
    SeasonService seasonService;

    @Override
    public void createAdminConfig() {
        AdminConfig adminConfig = new AdminConfig();
        adminConfigRepository.save(adminConfig);
    }

    @Override
    public AdminConfig getAdminConfig() {

        Optional<AdminConfig> result = adminConfigRepository.findById(1);

        AdminConfig adminConfig = null;

        if (result.isPresent()) {
            adminConfig = result.get();
        } else throw new RuntimeException("User Config not found");

        return adminConfig;
    }

    @Override
    public void setCurrentSeason(Season season) {
        AdminConfig adminConfig = getAdminConfig();
        adminConfig.setCurrentSeason(season);
        adminConfigRepository.save(adminConfig);
    }

    @Override
    public void setFavouriteTeam(Team team) {
        AdminConfig adminConfig = getAdminConfig();
        adminConfig.setFavouriteTeam(team);
        adminConfigRepository.save(adminConfig);
    }

    @Override
    public Team getFavouriteTeam() {
        return getAdminConfig().getFavouriteTeam();
    }

    @Override
    public int getCurrentSeasonId() {
        return getAdminConfig().getCurrentSeason().getId();
    }

    @Override
    public void setCurrentSeasonById(int id) {
        Season season = seasonService.getSeasonById(id);
        setCurrentSeason(season);
    }

    @Override
    public boolean getImportantMessageFlag() {
        //return true if the important message flag is set to true
        return getAdminConfig().isImportantMessageFlag();
    }

    @Override
    public void setImportantMessageFlag(boolean flag) {
        AdminConfig adminConfig = getAdminConfig();
        adminConfig.setImportantMessageFlag(flag);
        adminConfigRepository.save(adminConfig);
    }

    @Override
    public String getImportantMessageContent() {
        return getAdminConfig().getImportantMessageContent();
    }

    @Override
    public void setImportantMessageContent(String content) {
        AdminConfig adminConfig = getAdminConfig();
        adminConfig.setImportantMessageContent(content);
        adminConfigRepository.save(adminConfig);
    }

}
