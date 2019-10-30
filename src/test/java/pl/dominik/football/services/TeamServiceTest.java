package pl.dominik.football.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.dominik.football.domain.entity.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Test
    public void givenNewTeamWhenPolishCharsThenFindByIdNonNull() {
        final String TEAM_NAME = "ĘÓĄŚŁŻŹĆŃ ęóąśłżźćń";
        Team team = new Team();
        team.setId(1);
        team.setTeamName(TEAM_NAME);
        teamService.saveTeam(team);

        Team databaseTeam = teamService.getTeamById(1);

        Assert.assertNotNull(databaseTeam);
        Assert.assertEquals(TEAM_NAME, databaseTeam.getTeamName());
    }
}