package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.entity.Player;
import pl.dominik.football.services.PlayerService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    PlayerService playerService;

    @RequestMapping("/")
    public String homepage(Model model) {

        //show all players in table
        List<Player> allPlayers = playerService.getAllPlayers();
        model.addAttribute("players", allPlayers);
        return "index";
    }
}
