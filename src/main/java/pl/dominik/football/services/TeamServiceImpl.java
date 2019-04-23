package pl.dominik.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dominik.football.domain.entity.Team;
import pl.dominik.football.domain.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public void createTeam(String teamName) {
        Team team = new Team(teamName);
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
}
