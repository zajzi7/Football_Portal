package pl.dominik.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.dominik.football.domain.repository.*;
import pl.dominik.football.justfortestsomething.TeamRepository;

@Component
public class Start implements CommandLineRunner {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    UserConfigRepository userConfigRepository;

    @Override
    public void run(String... args) throws Exception {

        //manual creating round just for tests
        roundRepository.createRound(4);
        roundRepository.createRound(5);
        roundRepository.createRound(6);

        //manual creating season just for tests
        seasonRepository.createSeason("2018/2019");
        seasonRepository.createSeason("2019/2020");
        seasonRepository.addRound(
                roundRepository.getRoundByInt(4),
                seasonRepository.getSeason("2018/2019"));
        seasonRepository.addRound(
                roundRepository.getRoundByInt(5),
                seasonRepository.getSeason("2019/2020"));


        userConfigRepository.setCurrentSeason(seasonRepository.getSeason("2018/2019"));


        //manual creating players just for tests
        playerRepository.createPlayer("Dominik", "Drabina");
        playerRepository.createPlayer("Zdzislaw", "Mruk");

        //manual creating teams just for tests
        teamRepository.createTeam("Husaria");
        teamRepository.createTeam("Czarni");
        teamRepository.createTeam("Naprzod");
        teamRepository.createTeam("Wisla");

        //manual creating matches just for tests
        matchRepository.addMatchResult(
                teamRepository.getTeamByName("Husaria"),
                teamRepository.getTeamByName("Czarni"),
                3,2,
                roundRepository.getRoundByInt(4));

        matchRepository.addMatchResult(
                teamRepository.getTeamByName("Naprzod"),
                teamRepository.getTeamByName("Wisla"),
                1, 1,
                roundRepository.getRoundByInt(4));

        matchRepository.addMatchResult(
                teamRepository.getTeamByName("Wisla"),
                teamRepository.getTeamByName("Husaria"),
                1,4,
                roundRepository.getRoundByInt(4));

        //add matches to round
        /*roundRepository.addMatch(
                matchRepository.getMatchById(2),
                roundRepository.getRoundByInt(4));
        roundRepository.addMatch(
                matchRepository.getMatchById(1),
                roundRepository.getRoundByInt(4));
        */
    }
}
