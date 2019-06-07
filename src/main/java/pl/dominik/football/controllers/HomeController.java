package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.entity.Player;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.domain.repository.UserConfigRepository;
import pl.dominik.football.services.NewsService;
import pl.dominik.football.services.PlayerService;
import pl.dominik.football.utilities.SortTeams;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    PlayerService playerService;

    @Autowired
    NewsService newsService;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    UserConfigRepository userConfigRepository;

    @RequestMapping("/")
    public String homepage(Model model) {

        //Players
        List<Player> allPlayers = playerService.getAllPlayers();
        model.addAttribute("players", allPlayers);

        //News
        model.addAttribute("newsList", newsService.getAllNews());

        //Ranking
        int seasonId = userConfigRepository.getCurrentSeasonId();
        List<RankingData> rankingData = rankingDataRepository.getRankingDataBySeasonId(seasonId);
        rankingData.sort(new SortTeams());
        model.addAttribute("teams", rankingData);

        return "index2";
    }

}
