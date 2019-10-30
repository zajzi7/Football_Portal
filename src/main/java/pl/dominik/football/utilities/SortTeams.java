package pl.dominik.football.utilities;

import pl.dominik.football.domain.entity.H2h;
import pl.dominik.football.domain.entity.RankingData;

import java.util.Comparator;
import java.util.Set;

public class SortTeams implements Comparator<RankingData> {

    @Override
    public int compare(RankingData o1, RankingData o2) {

        int comparison = 0;

        //compare team points throughout the season
        Integer o2points = o2.getPoints();
        Integer o1points = o1.getPoints();
        comparison = o2points.compareTo(o1points);


        //compare head-to-head(h2h) points
        H2h o2H2h = null;
        H2h o1H2h = null;

        try {
            if (comparison == 0) {
                Set<H2h> o2H2hSet = o2.getH2h();
                Set<H2h> o1H2hSet = o1.getH2h();

                //Find H2h record in the database contains oppositeTeam such as the Team parameter of the second object
                for (H2h o2HeadToHead : o2H2hSet) {
                    if (o2HeadToHead.getOppositeTeam().getTeamName().equals(o1.getTeam().getTeamName())) {
                        o2H2h = o2HeadToHead;
                        break;
                    }
                }

                //The same as above and we will be able to compare these two h2h
                for (H2h o1HeadToHead : o1H2hSet) {
                    if (o1HeadToHead.getOppositeTeam() == o2.getTeam()) {
                        o1H2h = o1HeadToHead;
                        break;
                    }
                }

                //Main fragment compare h2h points
                Integer o2H2hPoints = o2H2h.getPointsH2h();
                Integer o1H2hPoints = o1H2h.getPointsH2h();
                comparison = o2H2hPoints.compareTo(o1H2hPoints);
            }

            //compare h2h goals difference
            if (comparison == 0) {
                Integer o2H2hDiff = o2H2h.getGoalsScoredH2h() - o2H2h.getGoalsConcededH2h();
                Integer o1H2hDiff = o1H2h.getGoalsScoredH2h() - o1H2h.getGoalsConcededH2h();
                comparison = o2H2hDiff.compareTo(o1H2hDiff);
            }

            //compare h2h goals away plus
            if (comparison == 0) {
                Integer o2H2hAwayPlus = o2H2h.getGoalsScoredAwayH2h();
                Integer o1H2hAwayPlus = o1H2h.getGoalsScoredAwayH2h();
                comparison = o2H2hAwayPlus.compareTo(o1H2hAwayPlus);
            }
        } catch (NullPointerException e) {
//            System.out.println("H2h null. o1 ID: " + o1.getId() + "; o2 ID: " + o2.getId());
//            e.printStackTrace();
        }
        //compare goals difference throughout the season
        if (comparison == 0) {
            Integer o2goalsDiff = o2.getGoalsScored() - o2.getGoalsConceded();
            Integer o1goalsDiff = o1.getGoalsScored() - o1.getGoalsConceded();
            comparison = o2goalsDiff.compareTo(o1goalsDiff);
        }

        //compare goals scored throughout the season
        if (comparison == 0) {
            Integer o2goalsScored = o2.getGoalsScored();
            Integer o1goalsScored = o1.getGoalsScored();
            comparison = o2goalsScored.compareTo(o1goalsScored);
        }

        return comparison;
    }
}