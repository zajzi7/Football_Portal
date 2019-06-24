package pl.dominik.football.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.UserConfigService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SeasonController {

    @Autowired
    UserConfigService userConfigService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    //Auxiliary variable to get String value from validation.messages.properties and add to the error
    @Value("${pl.season.validation.alreadyExist.message}")
    String seasonNameAlreadyExists;

    //Remove leading and trailing whitespace
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/newseason")
    //Form to create new season by admin
    public String createSeason(Model model) {
        model.addAttribute("season", new Season());
        return "create-season";
    }

    @RequestMapping(value = "/seasons", method = RequestMethod.POST)
    //Create new season in DB received from admin data and then redirect to season list
    public String saveSeason(
            @Valid @ModelAttribute("season") Season season,
            BindingResult bindingResult) {

        //Check if seasonName value entered by the admin contains errors(validation @NotNull, @Size in the Season class)
        if (bindingResult.hasErrors()) {
            return "create-season";
        }

        try {
            //Check if the seasonName value entered by the admin already exists
            //(handle @Column(unique=true) in the Season class)
            seasonService.saveSeason(season);

        } catch (DataIntegrityViolationException e) {
            //Season name already exists - add new error to the bindingResult
            ObjectError error = new ObjectError("seasonName", seasonNameAlreadyExists);
            bindingResult.addError(error);
            return "create-season";
        }

        //If everything went well then redirect to the seasons list
        return "redirect:/seasons";
    }

    @RequestMapping(value = "/seasons/edit/{id}")
    public String updateSeason(@PathVariable("id") int id, Model model) {
        Season season = seasonService.getSeasonById(id);
        model.addAttribute("season", season);
        return "create-season";
    }

    @RequestMapping(value = "/seasons/delete/{id}")
    public String deleteSeason(@PathVariable("id") int id) {
        seasonService.deleteSeason(id);
        return "redirect:/seasons";
    }

    @RequestMapping(value = "/seasons/set-season/{id}")
    public String setCurrentSeason(@PathVariable("id") int id) {
        userConfigService.setCurrentSeasonById(id);
        return "redirect:/seasons";
    }

    @RequestMapping("/seasons")
    //Show all seasons in table
    public String getAllSeasons(Model model) {

        //Get all seasons
        List<Season> seasons = seasonService.getSeasonList();
        model.addAttribute("seasons", seasons);

        //Get current season ID
        int currentSeasonId = userConfigService.getCurrentSeasonId();
        model.addAttribute("currentSeasonId", currentSeasonId);

        return "seasons";
    }

    @RequestMapping("/seasons/show-rounds/{id}")
    public String showRoundsBySeason(@PathVariable("id") int id, Model model) {
        List<Round> rounds = roundService.getRoundsBySeasonId(id);
        model.addAttribute("rounds", rounds);
        model.addAttribute("seasonId", id);
        model.addAttribute("seasonName", seasonService.getSeasonById(id).getSeasonName());
        return "rounds";
    }

}
