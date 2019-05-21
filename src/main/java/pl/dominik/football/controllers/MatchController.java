package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.TeamService;
import pl.dominik.football.utilities.RankingDataComponent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class MatchController {

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    @Autowired
    MatchService matchService;

    @Autowired
    TeamService teamService;

    @Autowired
    RankingDataComponent rankingDataComponent;

    @RequestMapping("/seasons/show-matches/{seasonId}/{roundId}")
    public String showMatchesByRoundId(
            @PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId, Model model) {
        model.addAttribute("season", seasonService.getSeasonById(seasonId));
        model.addAttribute("roundNumber", roundService.getRoundById(roundId).getRoundNumber());
        model.addAttribute("roundId", roundId);
        model.addAttribute("matches", matchService.getMatchesByRoundId(roundId));
        model.addAttribute("pausedTeams", teamService.getPausedTeamsInRound(roundId));
        return "matches";
    }

    @RequestMapping(value = "/matches/process/{seasonId}/{roundId}", method = RequestMethod.POST, params = "action=addMatches")
    //Create new match in DB and then redirect to list of matches
    public String createMatches(@PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId,
                                HttpServletRequest request) {

        int numberOfMatchesToAdd = Integer.parseInt(request.getParameter("numberOfMatchesToAdd"));
        for (; numberOfMatchesToAdd > 0; numberOfMatchesToAdd--) {
            Match match = new Match();
            match.setRound(roundService.getRoundById(roundId));
            //Initial match date is round date
            match.setMatchDate(roundService.getRoundById(roundId).getRoundStartDate());
            matchService.saveMatch(match);
        }
        return "redirect:/seasons/show-matches/" + seasonId + "/" + roundId;
    }

    @RequestMapping(value = "/matches/process/{seasonId}/{roundId}", method = RequestMethod.POST, params = "action=delete")
    public String deleteFromCheckboxes(@PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId,
                                       HttpServletRequest request) {
        if (request.getParameterValues("checkBoxMatchId") != null) {
            for (String matchId : request.getParameterValues("checkBoxMatchId")) {
                matchService.deleteMatchById(Integer.parseInt(matchId));
            }
        }
        return "redirect:/seasons/show-matches/" + seasonId + "/" + roundId;
    }

    @RequestMapping(value = "/matches/process/{seasonId}/{roundId}", method = RequestMethod.POST, params = "action=save")
    public String saveFromCheckboxes(@PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId,
                                     HttpServletRequest request) {

        if (request.getParameterValues("checkBoxMatchId") != null) {
            for (String matchId : request.getParameterValues("checkBoxMatchId")) {
                Match match = matchService.getMatchById(Integer.parseInt(matchId));

                //Create a copy of the match to help the RankingData if values are null
                Match matchCopy = new Match();
                matchCopy.setHomeTeam(match.getHomeTeam());
                matchCopy.setAwayTeam(match.getAwayTeam());
                matchCopy.setHomeScore(match.getHomeScore());
                matchCopy.setAwayScore(match.getAwayScore());
                matchCopy.setRound(match.getRound());

                int i = 0; //home goals id iteration
                int j = 0; //away goals id iteration
                int k = 0; //date id iteration
                int l = 0; //time id iteration

                /* 1) All data comes from view(even not selected checkboxes).
                matchId = selected checkboxes.
                2) Compare selected values to all incoming data then
                save only if matchId(selected checkbox) == data ID.
                Other values(not selected checkboxes) are skipped */

                //Home team
                for (String teamNameAndId : request.getParameterValues("homeTeamsFromSeasonSelector")) {
                    //Split th:option value - team name and match id (example: Husaria-3)
                    try {
                        String[] teamNameSplitId = teamNameAndId.split("-");
                        if (matchId.equals(teamNameSplitId[1])) {
                            match.setHomeTeam(teamService.getTeamByName(teamNameSplitId[0]));
                            //if team is null then set home team to null
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        continue;
                    }
                }

                //Home team score(goals)
                for (String homeScore : request.getParameterValues("homeScore")) {
                    try {
                        String homeScoreMatchId[] = request.getParameterValues("homeScoreId");
                        if (matchId.equals(homeScoreMatchId[i])) {
                            try {
                                match.setHomeScore(Integer.parseInt(homeScore));
                            } catch (NumberFormatException e) {
                                match.setHomeScore(null); //if score value is empty ''
                            }
                        }
                        i++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        continue;
                    }
                }

                //Away team score(goals)
                for (String awayScore : request.getParameterValues("awayScore")) {
                    try {
                        String awayScoreMatchId[] = request.getParameterValues("awayScoreId");
                        if (matchId.equals(awayScoreMatchId[j])) {
                            try {
                                match.setAwayScore(Integer.parseInt(awayScore));
                            } catch (NumberFormatException e) {
                                match.setAwayScore(null); //if score value is empty ''
                            }
                        }
                        j++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        continue;
                    }
                }

                //Away team
                for (String teamNameAndId : request.getParameterValues("awayTeamsFromSeasonSelector")) {
                    //Split th:option value - team name and match id (example: Husaria-3)
                    try {
                        String[] teamNameSplitId = teamNameAndId.split("-");
                        if (matchId.equals(teamNameSplitId[1])) {
                            match.setAwayTeam(teamService.getTeamByName(teamNameSplitId[0]));
                            //if team is null then set away team to null
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        continue;
                    }
                }

                //Date
                for (String matchDate : request.getParameterValues("matchDate")) {
                    String matchDateId[] = request.getParameterValues("matchDateId");
                    try {
                        if (matchId.equals(matchDateId[k])) {
                            match.setMatchDate(LocalDate.parse(matchDate));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        continue;
                    }
                    k++;
                }

                //Time
                for (String matchStartTime : request.getParameterValues("matchStartTime")) {
                    String matchStartTimeId[] = request.getParameterValues("matchStartTimeId");
                    try {
                        if (matchId.equals(matchStartTimeId[l]) && (!(matchStartTime.equals("")))) {
                            match.setMatchStartTime(LocalTime.parse(matchStartTime));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        continue;
                    }
                    l++;
                }


//              -----RankingData-----

//                1) if matchCopy is ok and the incoming match is ok
//                   update the ranking-data
                if (matchCopy.someValueIsNull() == false && match.someValueIsNull() == false) {
                    rankingDataComponent.undoMatch(matchCopy, true);
                    Match matchReversed = matchCopy.reverseMatch();
                    rankingDataComponent.undoMatch(matchReversed, false);
                    matchService.saveMatchAndAddRankingData(match);
                }

//                2) if matchCopy is ok and the incoming match is null
//                   undo match
                else if (matchCopy.someValueIsNull() == false && match.someValueIsNull() == true) {
                    rankingDataComponent.undoMatch(matchCopy, true);
                    Match matchReversed = matchCopy.reverseMatch();
                    rankingDataComponent.undoMatch(matchReversed, false);
                    matchService.saveMatch(match);
                }

//                3) if matchCopy is null and the incoming match is ok
//                   update the ranking-data
                else if (matchCopy.someValueIsNull() == true && match.someValueIsNull() == false) {
                    matchService.saveMatchAndAddRankingData(match);
                }

//                4) if match is null and the incoming match is null
//                   just save match to the database
                else if (matchCopy.someValueIsNull() == true && match.someValueIsNull() == true) {
                    matchService.saveMatch(match);
                }

            } //iterate to the next selected checkbox
        }
        return "redirect:/seasons/show-matches/" + seasonId + "/" + roundId;
    }

}
