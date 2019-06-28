package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;

public interface UserConfigService {

    void createUserConfig();

    void setCurrentSeason(Season season);

    void setFavouriteTeam(Team team);

    Team getFavouriteTeam();

    int getCurrentSeasonId();

    void setCurrentSeasonById(int id);

}
