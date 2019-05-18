package pl.dominik.football.utilities;

import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.RoundService;

import javax.persistence.PreRemove;
import java.util.Optional;

public class MatchEntityListener {

    @PreRemove
    private void undoRankingData(Match match) {

        //Workaround because can't use @Autowired in the EntityListeners
        RankingDataComponent rankingDataComponent = BeanUtil.getBean(RankingDataComponent.class);
        RankingDataRepository rankingDataRepository = BeanUtil.getBean(RankingDataRepository.class);
        RoundService roundService = BeanUtil.getBean(RoundService.class);

        RankingData ranking = null;

        //Find the proper ranking
        try {
            int rankingId = rankingDataComponent.getRankingIdContainsTeamIdByMatch(match);
            Optional<RankingData> result = rankingDataRepository.findById(rankingId);
            if (result.isPresent()) {
                ranking = result.get();
            } else {
                return;
            }
        } catch (NullPointerException e) { //rankingId exception
            return;
        } catch (IllegalArgumentException e) { //"Optional" exception
            return;
        }

        rankingDataComponent.undoMatch(match, true);
        Match matchReversed = match.reverseMatch();
        rankingDataComponent.undoMatch(matchReversed, false);
    }

}