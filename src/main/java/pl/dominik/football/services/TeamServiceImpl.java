package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.domain.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    RoundService roundService;

    @Autowired
    MatchService matchService;

    @Override
    public void createTeam(String teamName) {
        Team team = new Team(teamName);
        teamRepository.save(team);
    }

    @Override
    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team getTeamByName(String teamName) {
        return teamRepository.getTeamByTeamNameEquals(teamName);
    }

    @Override
    public Team getTeamById(int id) {
        Optional<Team> result = teamRepository.findById(id);

        Team team = null;

        if (result.isPresent()) {
            team = result.get();
        } else throw new RuntimeException("Team not found, ID: " + id);

        return team;
    }

    @Override
    public List<Team> getTeamsBySeasonId(int seasonId) {
        return teamRepository.getTeamsBySeason_Id(seasonId);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public List<Team> getPausedTeamsInRound(int roundId) {

        boolean teamIsNotPresentInMatchesFlag = false;
        List<Team> pausedTeams = new ArrayList<>();
        int seasonId = roundService.getSeasonIdByRoundId(roundId);
        List<Team> teams = getTeamsBySeasonId(seasonId);
        List<Match> matches = matchService.getMatchesByRoundId(roundId);

        for (Team team : teams) {
            for (Match match : matches) {
                if ((match.getHomeScore() != null && match.getAwayScore() != null) &&
                        (match.getHomeScore() >= 0 && match.getAwayScore() >= 0) &&
                        (match.getHomeTeam() != null && match.getAwayTeam() != null)) {

                    if (team.getId() == match.getAwayTeam().getId() || team.getId() == match.getHomeTeam().getId()) {
                        teamIsNotPresentInMatchesFlag = false;
                        break; //This team is present in matches of this round.
                        // Set flag to false to not save this team to pausedTeams and break to next team
                    }
                    teamIsNotPresentInMatchesFlag = true;
                }
            }
            if (teamIsNotPresentInMatchesFlag) {
                pausedTeams.add(team);
                teamIsNotPresentInMatchesFlag = false;
            }
        }
        return pausedTeams;
    }
}
