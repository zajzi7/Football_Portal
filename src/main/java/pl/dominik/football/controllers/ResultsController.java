package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.Match;
import pl.dominik.football.services.MatchService;

import java.util.List;

@Deprecated
@Controller
public class ResultsController {

    @Autowired
    MatchService matchService;

    @RequestMapping("/results")
    public String showResults(Model model) {

        //show results in table
        List<Match> matchesList = matchService.getMatchResultList();
        model.addAttribute("results", matchesList);
        return "results";
    }
}
