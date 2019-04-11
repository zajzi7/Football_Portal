package pl.dominik.football.services;

import pl.dominik.football.domain.entity.Team;

public interface TeamService {

    void createTeam(String teamName);

    void deleteTeam(int id);

    Team getTeamByName(String teamName);

    Team getTeamById(int id);

}
