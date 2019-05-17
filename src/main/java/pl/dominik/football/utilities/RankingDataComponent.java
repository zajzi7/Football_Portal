package pl.dominik.football.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dominik.football.domain.entity.H2h;
import pl.dominik.football.domain.entity.Match;
import pl.dominik.football.domain.entity.RankingData;
import pl.dominik.football.domain.repository.RankingDataRepository;
import pl.dominik.football.services.H2hService;
import pl.dominik.football.services.RoundService;

import java.util.Optional;

@Component
public class RankingDataComponent {

    @Autowired
    RoundService roundService;

    @Autowired
    RankingDataRepository rankingDataRepository;

    @Autowired
    H2hService h2hService;

    public void addMatch(Match match, boolean isHome) {
        RankingData ranking = null;

        //Find the RankingData
        try {
            int rankingId = getRankingIdContainsTeamIdByMatch(match);
            Optional<RankingData> result;
            result = rankingDataRepository.findById(rankingId);
            if (result.isPresent()) {
                ranking = result.get();
            }
        } catch (NullPointerException e) { //rankingId exception
            e.printStackTrace();
        } catch (IllegalArgumentException e) { //"Optional" exception
            e.printStackTrace();
        }

        //Matches played
        ranking.setMatchesPlayed(ranking.getMatchesPlayed() + 1);

        //Points, Win/Loss/Draw
        addMatchValues(match.getHomeScore(), match.getAwayScore(), ranking);

        //Goals
        ranking.setGoalsScored(ranking.getGoalsScored() + match.getHomeScore());
        ranking.setGoalsConceded(ranking.getGoalsConceded() + match.getAwayScore());

        //H2h
        //Find the H2h opposite team to set values(points h2h, goals h2h etc.)
        H2h h2hTeam = h2hService.getH2hByOppositeTeamAndRanking(match.getAwayTeam(), ranking);

        if (h2hTeam != null) {
            //How many goals have been scored against oppositeTeam
            h2hTeam.setGoalsScoredH2h(h2hTeam.getGoalsScoredH2h() + match.getHomeScore());
            h2hTeam.setGoalsConcededH2h(h2hTeam.getGoalsConcededH2h() + match.getAwayScore());

            if (!(isHome)) {
                h2hTeam.setGoalsScoredAwayH2h(h2hTeam.getGoalsScoredAwayH2h() + match.getHomeScore());
            }

            //Points h2h
            if (match.getHomeScore() > match.getAwayScore()) {
                //Win
                h2hTeam.setPointsH2h(h2hTeam.getPointsH2h() + 3);
            } else if (match.getHomeScore() < match.getAwayScore()) {
                //Lose
                h2hTeam.setPointsH2h(h2hTeam.getPointsH2h() + 0);
            } else {
                //Draw
                h2hTeam.setPointsH2h(h2hTeam.getPointsH2h() + 1);
            }
            h2hService.saveH2h(h2hTeam);
        }
        rankingDataRepository.save(ranking);
    }

    public void undoMatch(Match match, boolean isHome) {
        //Find the RankingData
        RankingData ranking = null;
        try {
            int rankingId = getRankingIdContainsTeamIdByMatch(match);
            Optional<RankingData> result;
            result = rankingDataRepository.findById(rankingId);
            if (result.isPresent()) {
                ranking = result.get();
            } else {
                return;
            }
        } catch (NullPointerException e) { //rankingId exception
            return;
        } catch (IllegalArgumentException e) { //"Optional" exception
            return;
        }

        //Undo values
        try {
            //Find h2h to undo h2h values
            H2h h2h = h2hService.getH2hByOppositeTeamAndRanking(match.getAwayTeam(), ranking);

            //Undo matches played, goals etc.
            ranking.setMatchesPlayed(ranking.getMatchesPlayed() - 1);
            ranking.setGoalsScored(ranking.getGoalsScored() - match.getHomeScore());
            ranking.setGoalsConceded(ranking.getGoalsConceded() - match.getAwayScore());

            h2h.setGoalsScoredH2h(h2h.getGoalsScoredH2h() - match.getHomeScore());
            h2h.setGoalsConcededH2h(h2h.getGoalsConcededH2h() - match.getAwayScore());
            if (!(isHome)) {
                h2h.setGoalsScoredAwayH2h(h2h.getGoalsScoredAwayH2h() - match.getHomeScore());
            }

            //Win/Lose/Draw
            if (match.getHomeScore() > match.getAwayScore()) {
                //Win
                ranking.setPoints(ranking.getPoints() - 3);
                ranking.setWon(ranking.getWon() - 1);
                h2h.setPointsH2h(h2h.getPointsH2h() - 3);

            } else if (match.getHomeScore() < match.getAwayScore()) {
                //Lose
                ranking.setPoints(ranking.getPoints() - 0);
                ranking.setLost(ranking.getLost() - 1);
                h2h.setPointsH2h(h2h.getPointsH2h() - 0);

            } else {
                //Draw
                ranking.setPoints(ranking.getPoints() - 1);
                ranking.setDrawn(ranking.getDrawn() - 1);
                h2h.setPointsH2h(h2h.getPointsH2h() - 1);
            }
            h2hService.saveH2h(h2h);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rankingDataRepository.save(ranking);
    }

    //Find a team in the database table RankingData to upgrade team points, goals etc.
    public int getRankingIdContainsTeamIdByMatch(Match match) {
        int seasonId = roundService.getSeasonIdByRoundId(match.getRound().getId()); //Find the Season by Round of the Match
        RankingData ranking = rankingDataRepository.getRankingDataByTeamIdAndSeasonId(
                match.getHomeTeam().getId(), seasonId);
        return ranking.getId();
    }

    private RankingData addMatchValues(int goalsHomeTeam, int goalsAwayTeam, RankingData ranking) {
        if (goalsHomeTeam > goalsAwayTeam) {
            //Win
            ranking.setPoints(ranking.getPoints() + 3);
            ranking.setWon(ranking.getWon() + 1);
        } else if (goalsHomeTeam < goalsAwayTeam) {
            //Lose
            ranking.setPoints(ranking.getPoints() + 0);
            ranking.setLost(ranking.getLost() + 1);
        } else {
            //Draw
            ranking.setPoints(ranking.getPoints() + 1);
            ranking.setDrawn(ranking.getDrawn() + 1);
        }
        return ranking;
    }
}