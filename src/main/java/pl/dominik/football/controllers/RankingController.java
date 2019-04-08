package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.services.RankingService;

@Controller
public class RankingController {

    @Autowired
    RankingService rankingService;

    @RequestMapping("/ranking")
    public String getRanking(Model model) {

        //show team ranking in table
        //List<Player> teamRanking = rankingService.generateRanking();
        //model.addAttribute("ranking", teamRanking);

        return "ranking";
    }
}
