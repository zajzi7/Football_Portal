package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.Match;
import pl.dominik.football.domain.repository.MatchRepository;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    public List<Match> getMatchResultList() {
        return matchRepository.getMatchResult();
    }
}
