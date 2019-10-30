package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.MatchRepository;
import pl.dominik.football.utilities.RankingDataComponent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    RoundService roundService;

    @Autowired
    RankingDataComponent rankingDataComponent;

    @Autowired
    AdminConfigService adminConfigService;

    @Override
    @Cacheable("empcache")
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
        //Initial match date is round date
        match.setMatchDate(roundService.getRoundById(round.getId()).getRoundStartDate());
        saveMatchAndAddRankingData(match);
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
    @Transactional
    public void saveMatchAndAddRankingData(Match match) {
        //Save match to the database
        matchRepository.save(match);

        //Reverse match to treat away team as home team
        Match reversedMatch = match.reverseMatch();

        //Add home team to the ranking
        rankingDataComponent.addMatch(match, true);
        rankingDataComponent.addMatch(reversedMatch, false);
    }

    @Override
    public void removeTeamFromMatchesBySeasonId(Team team, int seasonId) {
        //Used when removing team from the season
        List<Round> rounds = roundService.getRoundsBySeasonId(seasonId);
        List<Match> matches = null;

        //Iterate each round to then iterate each match and set the team in match to null
        for (Round r : rounds) {

            if (r.getMatches().isEmpty()) {
                break;
            } else {
                matches = matchRepository.getMatchesByRound_Id(r.getId());
            }

            for (Match m : matches) {

                if (m.getHomeTeam() != null &&
                        m.getHomeTeam().getTeamName().equals(team.getTeamName())) {

                    //Undo RankingData
                    if (m.getAwayTeam() != null) {
                        rankingDataComponent.undoMatch(m, true);
                        Match matchReversed = m.reverseMatch();
                        rankingDataComponent.undoMatch(matchReversed, false);
                    }

                    //Remove the Match from a Set of Teams(Team entity)
                    Set<Match> matchesHomeTeam = m.getHomeTeam().getMatchHomeTeam();
                    matchesHomeTeam.remove(m.getHomeTeam());
                    m.getHomeTeam().setMatchHomeTeam(matchesHomeTeam);

                    //Set the match to null
                    m.setHomeTeam(null);
                    matchRepository.save(m);
                }

                if (m.getAwayTeam() != null &&
                        m.getAwayTeam().getTeamName().equals(team.getTeamName())) {

                    //Undo RankingData
                    if (m.getHomeTeam() != null) {
                        rankingDataComponent.undoMatch(m, true);
                        Match matchReversed = m.reverseMatch();
                        rankingDataComponent.undoMatch(matchReversed, false);
                    }

                    //Remove the Match from a Set of Teams(Team entity)
                    Set<Match> matchesAwayTeam = m.getAwayTeam().getMatchAwayTeam();
                    matchesAwayTeam.remove(m.getAwayTeam());
                    m.getAwayTeam().setMatchAwayTeam(matchesAwayTeam);

                    //Set the match to null
                    m.setAwayTeam(null);

                    matchRepository.save(m);
                }
            }
        }
    }

    @Override
    public Match findPreviousMatch(int whichMatchFromList) {
        Team favouriteTeam = adminConfigService.getFavouriteTeam();

        //Get first element of the previous matches collection
        try {
            //GMT +00:15(not +02:00) because the duration of the match is 01:45min
            return matchRepository.findPreviousMatch(favouriteTeam, LocalDate.now(), LocalTime.now(ZoneId.of("GMT+00:15"))).get(whichMatchFromList);
        } catch (IndexOutOfBoundsException e) { //if there is no previous match
            return null;
        }
    }

    @Override
    public Match findNextMatch(int whichMatchFromList) {
        Team favouriteTeam = adminConfigService.getFavouriteTeam();

        //Get first element of the next matches collection(if the whichMatchFromList is 0)
        try {
            //GMT +00:15(not +02:00) because the duration of the match is 01:45min
            return matchRepository.findNextMatch(favouriteTeam, LocalDate.now(), LocalTime.now(ZoneId.of("GMT+00:15"))).get(whichMatchFromList);
        } catch (IndexOutOfBoundsException e) { //if there is no next match
            return null;
        }
    }

    @Override
    public List<Match> findLast5Matches(Team team, Season season) {

        //Find the last 5 matches from a given season(by the last round date)
        LocalDate roundDate;
        try {
            roundDate = roundService.getLastRound(season).getRoundStartDate();
        } catch (NullPointerException e) {
            roundDate = LocalDate.now();
        }

        List<Match> last5Matches = matchRepository.findLast5Matches(team, roundDate, PageRequest.of(0, 5));
        return last5Matches;
    }

}