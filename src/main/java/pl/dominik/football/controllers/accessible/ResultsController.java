package pl.dominik.football.controllers.accessible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.TeamService;
import pl.dominik.football.services.UserConfigService;

import java.util.List;

@Controller
public class ResultsController {

    @Autowired
    MatchService matchService;

    @Autowired
    RoundService roundService;

    @Autowired
    TeamService teamService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    UserConfigService userConfigService;

    @RequestMapping("/results")
    public String showResults(Model model, @RequestParam(defaultValue = "1") int round) {

        Season season = seasonService.getSeasonById(userConfigService.getCurrentSeasonId());
        Round selectedRound = roundService.getRoundBySeasonIdAndRoundNumber(season.getId(), round);
        List<Team> pausedTeamsInRound = teamService.getPausedTeamsInRound(selectedRound.getId());

        model.addAttribute("season", season);
        model.addAttribute("selectedRound", selectedRound);
        model.addAttribute("pausedTeamsInRound", pausedTeamsInRound);
        return "results";
    }
}
