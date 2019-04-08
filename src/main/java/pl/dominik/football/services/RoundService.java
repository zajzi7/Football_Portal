package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.Match;
import pl.dominik.football.domain.Round;
import pl.dominik.football.domain.repository.RoundRepository;

import java.util.List;

@Service
public class RoundService {

    @Autowired
    RoundRepository roundRepository;

    public List<Match> getMatchesByRound(Round round) {
        return roundRepository.getMatchesByRound(round);
    }

    public void createRound(int roundNumber) {
        roundRepository.createRound(roundNumber);
    }

    public Round getRoundByInt(int roundNumber) {
        return roundRepository.getRoundByInt(roundNumber);
    }

    public Round getRoundById(int id) {
        return roundRepository.getRoundById(id);
    }
}
