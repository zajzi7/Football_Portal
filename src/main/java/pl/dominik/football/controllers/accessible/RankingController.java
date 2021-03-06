package pl.dominik.football.controllers.accessible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.TeamService;
import pl.dominik.football.utilities.SortTeams;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingController {

    @Autowired
    TeamService teamService;

    @Autowired
    MatchService matchService;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    SeasonService seasonService;

    @RequestMapping("/ranking/{id}")
    public String getRanking(@PathVariable("id") int seasonId, Model model) {

        List<RankingData> rankingData = rankingDataRepository.getRankingDataBySeasonId(seasonId);
        rankingData.sort(new SortTeams());
        model.addAttribute("rankingData", rankingData);

        //5 matches
        List<List<Match>> last5MatchesList = new ArrayList<>();
        for (RankingData r : rankingData) {
            List<Match> matches = matchService.findLast5Matches(r.getTeam(), seasonService.getSeasonById(seasonId));
            last5MatchesList.add(matches);
        }
        model.addAttribute("last5MatchesList", last5MatchesList);

        return "ranking";
    }

}
