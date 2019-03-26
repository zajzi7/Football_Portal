package pl.dominik.football.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dominik.football.domain.Player;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homepage(Model model) {
        model.addAttribute("player", new Player("Dominik", "Drabina"));
        return "index";
    }
}
