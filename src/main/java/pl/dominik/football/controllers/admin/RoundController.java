package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class RoundController {

    //Auxiliary variable to get String value from validation.messages.properties and add to the error
    @Value("${pl.round.validation.alreadyExistInSeason.message}")
    String roundNumberAlreadyExists;

    @Autowired
    RoundService roundService;

    @Autowired
    SeasonService seasonService;

    @RequestMapping(value = "/newround/{id}")
    //Form to create new round by admin
    public String createRoundBySeasonId(@PathVariable("id") int id, Model model) {
        LocalDate date = roundService.generateNextRoundDefaultDate(seasonService.getSeasonById(id));

        model.addAttribute("round", new Round(date));
        model.addAttribute("seasonId", id);
        return "create-round";
    }

    @RequestMapping(value = "/rounds", method = RequestMethod.POST)
    //Create new round in DB received from admin data and then redirect to round list
    public String saveRound(
            @Valid @ModelAttribute("round") Round round,
            BindingResult bindingResult,
            int seasonId,
            Model model) {

        //Check if roundNumber value entered by the admin contains errors(validation @Min, @Max in the Round class)
        if (bindingResult.hasErrors()) {
            model.addAttribute("seasonId", seasonId);
            return "create-round";
        }

        try {
            //Check if the roundNumber value entered by the admin already exists in this season
            //(handle @UniqueConstraint in the Round class)
            roundService.assignRoundToSeasonWithId(round, seasonId);
        } catch (DataIntegrityViolationException e) {
            ObjectError error = new ObjectError("roundNumber", roundNumberAlreadyExists);
            bindingResult.addError(error);
            model.addAttribute("seasonId", seasonId);
            return "create-round";
        }

        //If everything went well then redirect to the rounds list
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