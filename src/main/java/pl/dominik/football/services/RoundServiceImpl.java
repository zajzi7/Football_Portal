package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.repository.RoundRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    SeasonService seasonService;


    public void assignRoundToSeasonWithId(Round round, int seasonId) {
        Season season = seasonService.getSeasonById(seasonId);

        round.setSeason(season); //Assign round to season
        roundRepository.save(round);
        seasonService.saveSeason(season);
    }

    @Override
    public void createRound(int roundNumber) {
        Round round = new Round(roundNumber);
        roundRepository.save(round);
    }

    @Override
    public void saveRound(Round round) {
        roundRepository.save(round);
    }

    @Override
    public Round getRoundByInt(int roundNumber) {
        return roundRepository.getRoundByRoundNumberEquals(roundNumber);
    }

    @Override
    public Round getRoundById(int id) {
        Optional<Round> result = roundRepository.findById(id);

        Round round = null;

        if (result.isPresent()) {
            round = result.get();
        } else throw new RuntimeException("Round not found, ID: " + id);

        return round;
    }

    @Override
    public List<Round> getRoundsBySeasonId(int id) {
        return roundRepository.getRoundsBySeason_Id(id);
    }

    @Override
    public void deleteRound(int id) {
        roundRepository.delete(getRoundById(id));
    }

    @Override
    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }
}
