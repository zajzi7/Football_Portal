package pl.dominik.football.utilities;

import org.junit.Assert;
import org.junit.Test;
import pl.dominik.football.domain.entity.RankingData;

import java.util.Arrays;
import java.util.List;

public class SortTeamsTest {
    @Test
    public void givenNewRankingWhenPointsAreEqualThenSortByGoalsDiff() {
        RankingData[] rankingData = {
                RankingData.builder().id(1).points(3).goalsScored(7).goalsConceded(9).build(),
                RankingData.builder().id(2).points(6).goalsScored(5).goalsConceded(2).build(),
                RankingData.builder().id(3).points(3).goalsScored(5).goalsConceded(2).build(),
                RankingData.builder().id(4).points(1).goalsScored(10).goalsConceded(2).build(),
                RankingData.builder().id(5).points(6).goalsScored(6).goalsConceded(3).build()};
        List<RankingData> ranking = Arrays.asList(rankingData);

        /*      Before sorting                             Expected after sorting
        #. Id | Points | G+ | G- | +/- |              #. Id | Points | G+ | G- | +/- |
        1)  1 |    3   |  7 |  9 | -2  |              1)  5 |    6   |  6 |  3 |  3  |
        2)  2 |    6   |  5 |  2 |  3  |              2)  2 |    6   |  5 |  2 |  3  |
        3)  3 |    3   |  5 |  2 |  3  |              3)  3 |    3   |  5 |  2 |  3  |
        4)  4 |    1   | 10 |  2 |  8  |              4)  1 |    3   |  7 |  9 | -2  |
        5)  5 |    6   |  6 |  3 |  3  |              5)  4 |    1   | 10 |  2 |  8  |  */

        Assert.assertEquals(ranking.get(0).getId(), 1);     //ID: 1 for the first position in the table
        Assert.assertEquals(ranking.get(2).getId(), 3);     //ID: 3 for the third position in the table

        ranking.sort(new SortTeams()); //Sorting

        Assert.assertEquals(ranking.get(0).getId(), 5);
        Assert.assertEquals(ranking.get(1).getId(), 2);
        Assert.assertEquals(ranking.get(2).getId(), 3);
        Assert.assertEquals(ranking.get(3).getId(), 1);
        Assert.assertEquals(ranking.get(4).getId(), 4);
    }
}