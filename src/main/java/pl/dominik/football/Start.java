package pl.dominik.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.dominik.football.domain.repository.UserConfigRepository;
import pl.dominik.football.services.*;

@Component
public class Start implements CommandLineRunner {

    @Autowired
    UserConfigRepository userConfigRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    @Autowired
    MatchService matchService;

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Override
    public void run(String... args) throws Exception {

        //manual creating round just for tests
        roundService.createRound(4);
        roundService.createRound(5);
        roundService.createRound(6);


        //manual creating season just for tests
        seasonService.createSeason("2018/2019");
        seasonService.createSeason("2019/2020");
        seasonService.addRound(
                roundService.getRoundByInt(4),
                seasonService.getSeason("2018/2019"));
        seasonService.addRound(
                roundService.getRoundByInt(5),
                seasonService.getSeason("2019/2020"));
        seasonService.addRound(
                roundService.getRoundByInt(6),
                seasonService.getSeason("2018/2019"));

        userConfigRepository.setCurrentSeason(seasonService.getSeason("2018/2019"));
//        System.out.println(seasonService.getSeasonById(1));


        //manual creating players just for tests
        playerService.createPlayer("Dominik", "Drabina");
        playerService.createPlayer("Zdzislaw", "Mruk");

        //manual creating teams just for tests
        teamService.createTeam("Husaria");
        teamService.createTeam("Czarni");
        teamService.createTeam("Naprzod");
        teamService.createTeam("Wisla");

        //manual creating matches just for tests
        matchService.addMatchResult(
                teamService.getTeamByName("Husaria"),
                teamService.getTeamByName("Czarni"),
                3,2,
                roundService.getRoundByInt(4));

        matchService.addMatchResult(
                teamService.getTeamByName("Naprzod"),
                teamService.getTeamByName("Wisla"),
                1, 1,
                roundService.getRoundByInt(4));

        matchService.addMatchResult(
                teamService.getTeamByName("Wisla"),
                teamService.getTeamByName("Husaria"),
                1,4,
                roundService.getRoundByInt(4));

        //add matches to round
        /*roundRepository.addMatch(
                matchRepository.getMatchById(2),
                roundRepository.getRoundByInt(4));
        roundRepository.addMatch(
                matchRepository.getMatchById(1),
                roundRepository.getRoundByInt(4));
        */

//        add teams to season
        seasonService.addTeam(teamService.getTeamById(1), seasonService.getSeasonById(1));
        seasonService.addTeam(teamService.getTeamById(2), seasonService.getSeasonById(1));
        seasonService.addTeam(teamService.getTeamById(3), seasonService.getSeasonById(1));
        seasonService.addTeam(teamService.getTeamById(4), seasonService.getSeasonById(1));
    }
}
