package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.repository.RankingRepository;

@Service
public class RankingService {

    @Autowired
    RankingRepository rankingRepository;

//    public List<Ranking> generateRanking() {
//        return new ArrayList<>(rankingRepository.generateRanking());
//    }
}
