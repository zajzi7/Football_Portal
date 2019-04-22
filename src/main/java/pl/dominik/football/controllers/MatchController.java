package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.RoundService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.TeamService;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/seasons/show-matches/{seasonId}/{roundId}")
    public String showMatchesByRoundId(
            @PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId, Model model) {
        model.addAttribute("season", seasonService.getSeasonById(seasonId));
        model.addAttribute("roundNumber", roundService.getRoundById(roundId).getRoundNumber());
        model.addAttribute("roundId", roundId);
        model.addAttribute("matches", matchService.getMatchesByRoundId(roundId));
        return "matches";
    }

    @RequestMapping("/newmatch/{seasonId}/{roundId}")
    public String createMatch(@PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId, Model model) {
        model.addAttribute("season", seasonService.getSeasonById(seasonId));
        model.addAttribute("roundId", roundId);
        model.addAttribute("match", new Match());
        return "create-match";
    }

    @RequestMapping(value = "/seasons/show-matches/{seasonId}/{roundId}", method = RequestMethod.POST)
    //Create new match in DB received from admin data and then redirect to list of matches
    public String saveSeason(@ModelAttribute("match") Match match,
                             @PathVariable("seasonId") int seasonId, @PathVariable("roundId") int roundId) {
        matchService.saveMatch(match);
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
                int i = 0; //home goals id iteration
                int j = 0; //away goals id iteration

                /* 1) All data comes from view(even not selected checkboxes).
                matchId = selected checkboxes.
                2) Compare selected values to all incoming data then
                save only if matchId(selected checkbox) == data ID.
                Other values(not selected checkboxes) are skipped */

                //Home team
                for (String teamNameAndId : request.getParameterValues("homeTeamsFromSeasonSelector")) {
                    //Split th:option value - team name and match id (example: Husaria-3)
                    String[] teamNameSplitId = teamNameAndId.split("-");
                    if (matchId.equals(teamNameSplitId[1])) {
                        match.setHomeTeam(teamService.getTeamByName(teamNameSplitId[0]));
                    }
                }

                //Home team score(goals)
                for (String homeScore : request.getParameterValues("homeScore")) {
                    String homeScoreMatchId[] = request.getParameterValues("homeScoreId");
                    if (matchId.equals(homeScoreMatchId[i])) {
                        match.setHomeScore(Integer.parseInt(homeScore));
                    }
                    i++;
                }

                //Away team score(goals)
                for (String awayScore : request.getParameterValues("awayScore")) {
                    String awayScoreMatchId[] = request.getParameterValues("awayScoreId");
                    if (matchId.equals(awayScoreMatchId[j])) {
                        match.setAwayScore(Integer.parseInt(awayScore));
                    }
                    j++;
                }

                //Away team
                for (String teamNameAndId : request.getParameterValues("awayTeamsFromSeasonSelector")) {
                    //Split th:option value - team name and match id (example: Husaria-3)
                    String[] teamNameSplitId = teamNameAndId.split("-");
                    if (matchId.equals(teamNameSplitId[1])) {
                        match.setAwayTeam(teamService.getTeamByName(teamNameSplitId[0]));
                    }
                }

                matchService.saveMatch(match); //Save match and iterate to next checkbox
            }
        }
        return "redirect:/seasons/show-matches/" + seasonId + "/" + roundId;
    }

}
