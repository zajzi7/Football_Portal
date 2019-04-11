package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.UserConfigService;

import java.util.List;

@Controller
public class SeasonController {

//    @Autowired
//    SeasonService seasonService;

    @Autowired
    UserConfigService userConfigService;

    @Autowired
    RoundService roundService;

    @Autowired
    SeasonService seasonService;

    @RequestMapping("/newseason")
    //Form to create new season by admin
    public String createSeason(Model model) {
        //TODO check if Season name already exist
        model.addAttribute("season", new Season());
        return "create-season";
    }

    @RequestMapping(value = "/seasons", method = RequestMethod.POST)
    //Create new season in DB received from admin data and then redirect to season list
    public String saveSeason(@ModelAttribute("season") Season season) {
        seasonService.saveSeason(season);
        return "redirect:/seasons";
    }

    @RequestMapping(value = "/seasons/edit/{id}")
    public String updateSeason(@PathVariable("id") int id, Model model) {
        //TODO check if Season name already exist
        model.addAttribute("season", seasonService.getSeasonById(id));
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

}
