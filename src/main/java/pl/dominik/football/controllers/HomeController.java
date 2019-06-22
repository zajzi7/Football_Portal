package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.repository.NewsRepository;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.NewsService;
import pl.dominik.football.services.PlayerService;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.UserConfigService;
import pl.dominik.football.utilities.SortTeams;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserConfigService userConfigService;

    @Autowired
    PlayerService playerService;

    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    @Autowired
    MatchService matchService;

    @RequestMapping("/")
    public String homepage(Model model, @RequestParam(defaultValue = "1") int page) {

        int seasonId = userConfigService.getCurrentSeasonId();
        Season season = seasonService.getSeasonById(seasonId);


        //News
        model.addAttribute("newsList", newsRepository.findAll(PageRequest.of(page - 1, 6)));
        model.addAttribute("currentPage", page);


        //Ranking
        List<RankingData> rankingData = rankingDataRepository.getRankingDataBySeasonId(seasonId);
        rankingData.sort(new SortTeams());
        model.addAttribute("teams", rankingData);


        //Previous match
        Match previousMatch;
        boolean previousMatchScoreIsNull = true;
        int i = 0;
        try {
            do {
                previousMatch = matchService.getMatchById(matchService.findPreviousMatch(i).getId());
                if (previousMatch.getHomeTeam() == null || previousMatch.getAwayTeam() == null) {
                    previousMatch = null;
                }
                i++;
            } while (previousMatch == null);
            if (previousMatch.getHomeScore() != null && previousMatch.getAwayScore() != null) {
                previousMatchScoreIsNull = false;
            }
        } catch (NullPointerException e) {
            previousMatch = null;
        }
        model.addAttribute("previousMatch", previousMatch);
        model.addAttribute("previousMatchScoreIsNull", previousMatchScoreIsNull);


        //Next Match
        Match nextMatch;
        boolean nextMatchScoreIsNull = true;
        int j = 0;
        try {
            do {
                nextMatch = matchService.getMatchById(matchService.findNextMatch(j).getId());
                if (nextMatch.getHomeTeam() == null || nextMatch.getAwayTeam() == null) {
                    nextMatch = null;
                }
                j++;
            } while (nextMatch == null);
            if (nextMatch.getHomeScore() != null && nextMatch.getAwayScore() != null) {
                nextMatchScoreIsNull = false;
            }
        }
        catch (NullPointerException e) {
            nextMatch = null;
        }
        model.addAttribute("nextMatch", nextMatch);
        model.addAttribute("nextMatchScoreIsNull", nextMatchScoreIsNull);


        //Last round
        Round lastRound = roundService.getLastRound(season);
        model.addAttribute("lastRound", lastRound);


        return "index2";
    }

}
