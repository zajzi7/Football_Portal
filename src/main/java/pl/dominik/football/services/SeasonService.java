package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;

import java.util.List;

public interface SeasonService {

    void createSeason(String seasonName);

    void saveSeason(Season season);

    List<Season> getSeasonList();

    void deleteSeason(int id);

    Season getSeasonById(int id);

    Season getSeason(String seasonName);

    void addRound(Round round, Season season);

}