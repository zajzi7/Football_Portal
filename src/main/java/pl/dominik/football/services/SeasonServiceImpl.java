package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.repository.RoundRepository;
import pl.dominik.football.domain.repository.SeasonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    RoundRepository roundRepository;

    @Override
    public void createSeason(String seasonName) {
        Season season = new Season(seasonName);
        seasonRepository.save(season);
    }

    @Override
    public void saveSeason(Season season) {
        seasonRepository.save(season);
    }

    @Override
    public List<Season> getSeasonList() {
        return seasonRepository.findAll();
    }

    @Override
    public void deleteSeason(int id) {
        seasonRepository.deleteById(id);
    }

    @Override
    public Season getSeasonById(int id) {

        Optional<Season> result = seasonRepository.findById(id);

        Season season = null;

        if (result.isPresent()) {
            season = result.get();
        } else throw new RuntimeException("Season not found, ID: " + id);

        return season;
    }

    @Override
    public Season getSeason(String seasonName) {
        return seasonRepository.getSeasonBySeasonNameEquals(seasonName);
    }

    @Override
    public void addRound(Round round, Season season) {
        //TODO probably this method will be moved to RoundRepository
        season.addRound(round);
        round.setSeason(season);
        roundRepository.save(round);
        seasonRepository.save(season);
    }

}
