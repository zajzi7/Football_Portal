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

    @RequestMapping("/newround")
    //Form to create new round by admin
    //TODO this will be deleted. It's here only for tests
    public String createRound(Model model) {
        model.addAttribute("round", new Round());
        return "create-round";
    }

    @RequestMapping(value = "/newround/{id}")
    //Form to create new round by admin
    public String createRoundBySeasonId(@PathVariable("id") int id, Model model) {
        //TODO check if Round name already exist
        model.addAttribute("round", new Round());
        model.addAttribute("seasonId", id);
        return "create-round";
    }

    @RequestMapping(value = "/rounds", method = RequestMethod.POST)
    //Create new round in DB received from admin data and then redirect to round list
    public String saveRound(@ModelAttribute("round") Round round, int seasonId) {
        roundService.assignRoundToSeasonWithId(round, seasonId);
        return "redirect:/seasons/show-rounds/" + seasonId;
    }

    @RequestMapping(value = "/rounds/edit/{id}")
    public String updateRound(@PathVariable("id") int id, Model model) {
        //TODO check if Round name already exist
        model.addAttribute("round", roundService.getRoundById(id));
        return "create-round";
    }

    @RequestMapping(value = "/rounds/delete/{id}")
    public String deleteRound(@PathVariable("id") int id) {
        roundService.deleteRound(id);
        return "redirect:/rounds";
    }

    @RequestMapping("/rounds")
    //TODO this will be deleted. It's here only for tests
    //Show all rounds in table
    public String getAllRounds(Model model) {
        //Get all rounds
        List<Round> rounds = roundService.getAllRounds();
        model.addAttribute("rounds", rounds);
        return "rounds";
    }



}