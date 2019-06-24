package pl.dominik.football.controllers.accessible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.NewsService;
import pl.dominik.football.services.PlayerService;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.UserConfigService;
import pl.dominik.football.utilities.SortTeams;

import java.util.List;

@ControllerAdvice(basePackages = {"pl.dominik.football.controllers.accessible"})
public class AdviceController {

    @Autowired
    UserConfigService userConfigService;

    @Autowired
    PlayerService playerService;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    @Autowired
    MatchService matchService;

    //Ranking
    @ModelAttribute("teams")
    public List<RankingData> rankingTeams() {
        int seasonId = userConfigService.getCurrentSeasonId();
        List<RankingData> rankingData = rankingDataRepository.getRankingDataBySeasonId(seasonId);
        rankingData.sort(new SortTeams());
        return rankingData;
    }

    //Previous match
    @ModelAttribute("previousMatch")
    public Match previousMatch() {
        Match previousMatch;
        int i = 0;
        try {
            do {
                previousMatch = matchService.getMatchById(matchService.findPreviousMatch(i).getId());
                if (previousMatch.getHomeTeam() == null || previousMatch.getAwayTeam() == null) {
                    previousMatch = null;
                }
                i++;
            } while (previousMatch == null);
        } catch (NullPointerException e) {
            previousMatch = null;
        }
        return previousMatch;
    }

    //Next Match
    @ModelAttribute("nextMatch")
    public Match nextMatch() {
        Match nextMatch;
        int j = 0;
        try {
            do {
                nextMatch = matchService.getMatchById(matchService.findNextMatch(j).getId());
                if (nextMatch.getHomeTeam() == null || nextMatch.getAwayTeam() == null) {
                    nextMatch = null;
                }
                j++;
            } while (nextMatch == null);
        }
        catch (NullPointerException e) {
            nextMatch = null;
        }
        return nextMatch;
    }

    //Last round
    @ModelAttribute("lastRound")
    public Round lastRound() {
        int seasonId = userConfigService.getCurrentSeasonId();
        Season season = seasonService.getSeasonById(seasonId);
        Round lastRound = roundService.getLastRound(season);
        return lastRound;
    }

}
