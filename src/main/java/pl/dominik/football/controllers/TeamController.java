package pl.dominik.football.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.MatchService;
import pl.dominik.football.services.SeasonService;
import pl.dominik.football.services.TeamService;
import pl.dominik.football.utilities.RankingDataComponent;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    MatchService matchService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RankingDataComponent rankingDataComponent;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @RequestMapping("/newteam")
    //Form to create new team by admin
    public String createTeam(Model model) {
        model.addAttribute("team", new Team());
        return "create-team";
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST, params = "action=editTeam")
    public String editTeam(@ModelAttribute("team") Team team) {
        List<Team> allTeams = teamService.getAllTeams();

        for (Team t : allTeams) {
            if (t.getTeamName().equals(team.getTeamName())) {
                //loop to check if team name entered by admin already exists
                return "error-name";
            }
        }

        Set<Season> seasons = seasonService.getSeasonsContainsTeam(team);
        team.setSeason(seasons); //Don't delete! Workaround necessary to edit team

        teamService.saveTeam(team);
        return "redirect:/teams";
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST, params = "action=newTeam")
    //Create new team in DB received from admin data and then redirect to season list
    public String saveTeam(@ModelAttribute("team") Team team) {
        List<Team> allTeams = teamService.getAllTeams();

        for (Team t : allTeams) {
            if (t.getTeamName().equals(team.getTeamName())) {
                //loop to check if team name entered by admin already exists
                return "error-name";
            }
        }
        teamService.saveTeam(team);
        return "redirect:/teams";
    }

    @RequestMapping(value = "/teams")
    public String getAllTeams(Model model) {
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "teams";
    }

    @RequestMapping(value = "/teams/edit/{id}")
    public String updateTeam(@PathVariable("id") int id, Model model) {
        model.addAttribute("team", teamService.getTeamById(id));
        return "create-team";
    }

    @RequestMapping(value = "/teams/delete/{id}")
    public String deleteTeam(@PathVariable("id") int id) {
        teamService.deleteTeam(id);
        return "redirect:/teams";
    }

    @RequestMapping("/assign-teams/{id}")
    public String assignTeamsToSeason(@PathVariable("id") int seasonId, Model model) {

        Season season = seasonService.getSeasonById(seasonId);

        Set<Team> seasonTeams = season.getTeams();
        List<Team> allTeams = teamService.getAllTeams();
        Set<Team> allTeamsWithoutSeasonTeams = new HashSet<>();

        for (Team t : allTeams) {
            if (!(seasonTeams.contains(t))) {
                allTeamsWithoutSeasonTeams.add(t);
            }
        }
        model.addAttribute("season", season);
        model.addAttribute("allTeams", allTeamsWithoutSeasonTeams);
        return "assign-teams";
    }

    @RequestMapping(value = "/assign-teams/{id}", method = RequestMethod.POST, params = "action=leftToRight")
    public String assignTeamsToSeason(@PathVariable("id") int seasonId, HttpServletRequest request) {
        //assign teams to the season by ">>" button and selected values from multiple input
        Season season = seasonService.getSeasonById(seasonId);

        try {
            for (String teamName : request.getParameterValues("allTeamsList")) {
                Team team = teamService.getTeamByName(teamName);

                if (!(season.getTeams().contains(team))) {
                    seasonService.addTeam(team, season);
                }
            }
        } catch (NullPointerException e) { //when none of the values is selected
//            e.printStackTrace();
        }
        return "redirect:/assign-teams/" + seasonId;
    }

    @RequestMapping(value = "/assign-teams/{id}", method = RequestMethod.POST, params = "action=rightToLeft")
    public String removeTeamsFromSeason(@PathVariable("id") int seasonId, HttpServletRequest request) {
        //remove teams from the season by "<<" button and selected values from multiple input
        Season season = seasonService.getSeasonById(seasonId);

        try {
            for (String teamName : request.getParameterValues("seasonTeamsList")) {
                Team team = teamService.getTeamByName(teamName);

                if (season.getTeams().contains(team)) {

                    //remove the team from matches
                    matchService.removeTeamFromMatchesBySeasonId(team, seasonId);

                    //remove the season from the team
                    team.removeFromSeason(season);

                    //remove the team RankingData
                    RankingData ranking = rankingDataRepository.getRankingDataByTeamIdAndSeasonId(team.getId(), seasonId);
                    if (ranking != null) {
                        rankingDataRepository.delete(ranking);
                    }

                    teamService.saveTeam(team); //seasonService.saveSeason is unnecessary

                    //remove the team from the season
                    season.removeTeam(team);
                }
            }
        } catch (NullPointerException e) { //when none of the values is selected
//            e.printStackTrace();
        }

        return "redirect:/assign-teams/" + seasonId;
    }

}

