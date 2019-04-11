package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.repository.RoundRepository;

import java.util.Optional;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Override
    public void createRound(int roundNumber) {
        Round round = new Round(roundNumber);
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
}
