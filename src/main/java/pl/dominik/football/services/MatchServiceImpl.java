package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.MatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    RoundService roundService;

    @Autowired
    TeamService teamService;

    @Override
    public Match getMatchById(int id) {
        Optional<Match> result = matchRepository.findById(id);

        Match match = null;

        if (result.isPresent()) {
            match = result.get();
        } else throw new RuntimeException("Match not found, ID: " + id);

        return match;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void addMatchResult(Team homeTeam, Team awayTeam, int homeScore, int awayScore, Round round) {
        Match match = new Match(homeTeam, awayTeam, homeScore, awayScore, round);
        matchRepository.save(match);
    }

    public List<Match> getMatchesByRoundId(int roundId) {
        return matchRepository.getMatchesByRound_Id(roundId);
    }

    @Override
    public void deleteMatchById(int id) {
        matchRepository.deleteById(id);
    }

    @Override
    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    @Override
    public List<Team> getPausedTeamsInRound(int roundId) {

        boolean teamIsNotPresentInMatchesFlag = false;
        List<Team> pausedTeams = new ArrayList<>();
        int seasonId = roundService.getSeasonIdByRoundId(roundId);
        List<Team> teams = teamService.getTeamsBySeasonId(seasonId);
        List<Match> matches = matchRepository.getMatchesByRound_Id(roundId);

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