package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.dominik.football.services.RoundService;

@Controller
public class RoundController {

    @Autowired
    RoundService roundService;

//    @RequestMapping("/rounds")
//    public String
}
