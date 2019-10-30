package pl.dominik.football.services;

import pl.dominik.football.domain.entity.AdminConfig;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;

public interface AdminConfigService {

    void createAdminConfig();

    AdminConfig getAdminConfig();

    void setCurrentSeason(Season season);

    void setFavouriteTeam(Team team);

    Team getFavouriteTeam();

    int getCurrentSeasonId();

    void setCurrentSeasonById(int id);

    boolean getImportantMessageFlag();

    void setImportantMessageFlag(boolean flag);

    void setImportantMessageContent(String content);

    String getImportantMessageContent();

}
