package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.Round;
import pl.dominik.football.domain.entity.Season;
import pl.dominik.football.domain.entity.Team;

import java.util.List;

public interface MatchService {

    Match getMatchById(int id);

    List<Match> getAllMatches();

    void addMatchResult(Team homeTeam, Team awayTeam, int homeScore, int awayScore, Round round);

    List<Match> getMatchesByRoundId(int roundId);

    void deleteMatchById(int id);

    void saveMatch(Match match);

    void saveMatchAndAddRankingData(Match match);

    void removeTeamFromMatchesBySeasonId(Team team, int seasonId);

    Match findPreviousMatch(int whichMatchFromList);

    Match findNextMatch(int whichMatchFromList);

    List<Match> findLast5Matches(Team team, Season season);

}
