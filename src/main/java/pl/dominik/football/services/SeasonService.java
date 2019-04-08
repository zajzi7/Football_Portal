package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.Round;
import pl.dominik.football.domain.Season;
import pl.dominik.football.domain.repository.SeasonRepository;

import java.util.List;

@Service
public class SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

    public void createSeason(String seasonName) {
        seasonRepository.createSeason(seasonName);
    }

    @Transactional
    public void saveSeason(Season season) {
        seasonRepository.saveSeason(season);
    }

    public List<Season> getSeasonList() {
        return seasonRepository.getSeasonList();
    }

    public void deleteSeason(int id) {
        seasonRepository.deleteSeason(id);
    }

    public Season getSeasonById(int id) {
        return seasonRepository.getSeasonById(id);
    }

    public Season getSeason(String seasonName) {
        return seasonRepository.getSeason(seasonName);
    }

    public void addRound(Round round, Season season) {
        seasonRepository.addRound(round, season);
    }
}
