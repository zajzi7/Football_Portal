package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.services.RoundService;

import java.util.List;

@Controller
public class RoundController {

    @Autowired
    RoundService roundService;

    @RequestMapping(value = "/newround/{id}")
    //Form to create new round by admin
    public String createRoundBySeasonId(@PathVariable("id") int id, Model model) {
        model.addAttribute("round", new Round());
        model.addAttribute("seasonId", id);
        return "create-round";
    }

    @RequestMapping(value = "/rounds", method = RequestMethod.POST)
    //Create new round in DB received from admin data and then redirect to round list
    public String saveRound(@ModelAttribute("round") Round round, int seasonId) {
        List<Round> rounds = roundService.getRoundsBySeasonId(seasonId);

        for (Round r : rounds) {
            if (r.getRoundNumber() == round.getRoundNumber()) {
                //loop to check if round number entered by admin already exists in this season
                return "error-name";
            }
        }

        roundService.assignRoundToSeasonWithId(round, seasonId);
        return "redirect:/seasons/show-rounds/" + seasonId;
    }

    @RequestMapping(value = "/rounds/edit/{seasonId}/{roundId}")
    public String updateRound
            (@PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId, Model model) {
        Round round = roundService.getRoundById(roundId);

        model.addAttribute("round", round);
        model.addAttribute("seasonId", seasonId);
        return "create-round";
    }

    @RequestMapping(value = "/rounds/delete/{seasonId}/{roundId}")
    public String deleteRound(@PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId) {
        roundService.deleteRound(roundId);
        return "redirect:/seasons/show-rounds/" + seasonId;
    }

}