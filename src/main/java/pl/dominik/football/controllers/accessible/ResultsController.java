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

import java.util.Collections;
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

        //Sort the season rounds by date
        List<Round> rounds = season.getRounds();
        Collections.sort(rounds, (o1, o2) -> {
            if (o1.getRoundStartDate().isBefore(o2.getRoundStartDate())) {
                return -1;
            }
            return 0;
        });

        model.addAttribute("season", season);
        model.addAttribute("rounds", rounds);
        model.addAttribute("selectedRound", selectedRound);
        model.addAttribute("pausedTeamsInRound", pausedTeamsInRound);
        return "results";
    }
}
