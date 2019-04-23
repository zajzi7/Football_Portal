package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.RoundService;

import java.util.List;

@Deprecated
@Controller
public class ResultsController {

    @Autowired
    MatchService matchService;

    @Autowired
    RoundService roundService;

    @RequestMapping("/results")
    public String showResults(Model model) {

        //show results in table
        //TODO choose which round to display
        List<Match> matchesList = matchService.getAllMatches();
        model.addAttribute("results", matchesList);
        model.addAttribute("pausedTeams", matchService.getPausedTeamsInRound(1));
        return "results";
    }
}
