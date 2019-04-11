package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.MatchRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Override
    public Match getMatchById(int id) {
        Optional<Match> result = matchRepository.findById(id);

        Match match = null;

        if (result.isPresent()) {
            match = result.get();
        } else throw new RuntimeException("Match not found, ID: " + id);

        return match;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void addMatchResult(Team homeTeam, Team awayTeam, int homeScore, int awayScore, Round round) {
        Match match = new Match(homeTeam, awayTeam, homeScore, awayScore, round);
        matchRepository.save(match);
    }

    @Override
    public List<Match> getMatchesByRound(Round round) {
        return matchRepository.getMatchesByRound(round);
    }


}
